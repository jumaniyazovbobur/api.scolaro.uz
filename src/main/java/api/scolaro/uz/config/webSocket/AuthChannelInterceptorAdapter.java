package api.scolaro.uz.config.webSocket;

/**
 * @author 'Mukhtarov Sarvarbek' on 21.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */

import api.scolaro.uz.config.details.CustomUserDetailsService;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.JwtDTO;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private final String TOKEN_HEADER = "X-Auth";

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthChannelInterceptorAdapter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.debug("Intercepting new message, " + message.getPayload());
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            log.error("accessor is null");
            return null;
        } else if (accessor.getCommand() == StompCommand.CONNECT) {
            final String tokenWithBearer = accessor.getFirstNativeHeader(TOKEN_HEADER);
            if (tokenWithBearer == null) {
                log.error("token is null");
                return null;
            }
            final String token = tokenWithBearer.substring(7).trim();
            JwtDTO jwtDTO = JwtUtil.decode(token);
            // load user depending on role
            UserDetails userDetails;
            String phone = jwtDTO.getPhone();
            if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, jwtDTO.getRoleList())) {  // load consulting
                userDetails = customUserDetailsService.loadConsultingByPhone(phone);
            } else { // load student or admin
                userDetails = customUserDetailsService.loadUserByUsername(phone);
            }
            final UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            accessor.setUser(user);
        } else if (accessor.getCommand() == StompCommand.SEND) {
            if (accessor.getUser() == null || accessor.getUser().getName().isEmpty()) {
                log.debug("User not authorized - preventing message");
                return null;
            }
        }

        return message;
    }

}
