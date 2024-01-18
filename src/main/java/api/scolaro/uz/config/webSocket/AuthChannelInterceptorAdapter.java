package api.scolaro.uz.config.webSocket;

/**
 * @author 'Mukhtarov Sarvarbek' on 21.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */

import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.config.details.CustomUserDetailsService;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.JwtDTO;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.repository.consulting.ConsultingProfileRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import api.scolaro.uz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private final ConsultingProfileRepository consultingProfileRepository;
    private final ProfileRepository profileRepository;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthChannelInterceptorAdapter(ConsultingProfileRepository consultingProfileRepository,
                                         ProfileRepository profileRepository, CustomUserDetailsService userDetailsService) {

        this.consultingProfileRepository = consultingProfileRepository;
        this.profileRepository = profileRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, @NotNull MessageChannel channel) {
        log.debug("Intercepting new message, " + message.getPayload());
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            log.error("accessor is null");
            return null;
        } else if (accessor.getCommand() == StompCommand.CONNECT) {
            log.info("connect user");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) accessor.getHeader("simpUser");
            accessor.setUser(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken == null) {
                usernamePasswordAuthenticationToken = getUser(accessor);
                if (usernamePasswordAuthenticationToken == null) {
                    return message;
                }
            }
            CustomUserDetails user = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
            List<String> roleList = user.getRoleList().stream().map(SimpleGrantedAuthority::getAuthority).toList();
            if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, roleList)) {  // load consulting
                consultingProfileRepository.updateIsOnline(user.getId(), Boolean.TRUE);
            } else { // load student or admin
                profileRepository.updateIsOnline(user.getId(), Boolean.TRUE);
            }
        } else if (accessor.getCommand() == StompCommand.SEND) {
            if (accessor.getUser() == null || accessor.getUser().getName().isEmpty()) {
                log.debug("User not authorized - preventing message");
                return null;
            }
        } else if (accessor.getCommand() == StompCommand.DISCONNECT) {
            log.info("disconnect user");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) accessor.getHeader("simpUser");
            if (usernamePasswordAuthenticationToken == null) {
                usernamePasswordAuthenticationToken = getUser(accessor);
                if (usernamePasswordAuthenticationToken == null) {
                    return message;
                }
            }
            CustomUserDetails user = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
            if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, user.getRoleList().stream().map(SimpleGrantedAuthority::getAuthority).toList())) {  // load consulting
                consultingProfileRepository.updateIsOnline(user.getId(), false);
            } else { // load student or admin
                profileRepository.updateIsOnline(user.getId(), false);
            }
        }
        return message;
    }

    private UsernamePasswordAuthenticationToken getUser(StompHeaderAccessor accessor) {
        List<String> nativeHeader = accessor.getNativeHeader("Authorization");
        if (nativeHeader != null && !nativeHeader.isEmpty()) {
            String token = nativeHeader.get(0);
            return getUserFromToken(token);
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken getUserFromToken(String token) {
        token = token.substring(7).trim();
        JwtDTO jwtDTO = JwtUtil.decode(token);
        // load user depending on role
        UserDetails userDetails;
        String phone = jwtDTO.getPhone();
        if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, jwtDTO.getRoleList())) {  // load consulting
            userDetails = userDetailsService.loadConsultingByPhone(phone);
        } else { // load student or admin
            userDetails = userDetailsService.loadUserByUsername(phone);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}