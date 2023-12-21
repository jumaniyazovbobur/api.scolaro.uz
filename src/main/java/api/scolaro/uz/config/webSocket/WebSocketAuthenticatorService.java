package api.scolaro.uz.config.webSocket;

/**
 * @author 'Mukhtarov Sarvarbek' on 21.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */

import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.repository.profile.PersonRoleRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class WebSocketAuthenticatorService {

    private final ProfileRepository userRepository;
    private final PersonRoleRepository personRoleRepository;

    @Autowired
    public WebSocketAuthenticatorService(ProfileRepository userRepository, PersonRoleRepository personRoleRepository) {
        this.userRepository = userRepository;
        this.personRoleRepository = personRoleRepository;
    }

    @Transactional
    public UsernamePasswordAuthenticationToken getAuthenticated(final String username)
            throws AuthenticationException {
        log.info("adsasdada");
        log.info(username);
        if (username == null || username.trim().isEmpty()) {
            String principal = UUID.randomUUID().toString();
            log.debug("Credential headers either null or empty, assigning null principal: ");
            return new UsernamePasswordAuthenticationToken(null, null);
        }
        log.debug("Fetching user from database: " + username);
        Optional<ProfileEntity> profileOptional = userRepository.findByPhoneAndVisibleIsTrue(username);
        if (profileOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profileEntity = profileOptional.get();
        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(profileEntity.getId());

        log.debug("Assigning principal: " + username);
        log.debug("Assigning authorities: " + roleList);
        return new UsernamePasswordAuthenticationToken(username, null, roleList.stream().map(item -> new SimpleGrantedAuthority(item.name())).toList());

    }
    @Transactional
    public UsernamePasswordAuthenticationToken loadConsultingByPhone(final String username)
            throws AuthenticationException {
        log.info("adsasdada");
        log.info(username);
        if (username == null || username.trim().isEmpty()) {
            String principal = UUID.randomUUID().toString();
            log.debug("Credential headers either null or empty, assigning null principal: ");
            return new UsernamePasswordAuthenticationToken(null, null);
        }
        log.debug("Fetching user from database: " + username);
        Optional<ProfileEntity> profileOptional = userRepository.findByPhoneAndVisibleIsTrue(username);
        if (profileOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profileEntity = profileOptional.get();
        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(profileEntity.getId());

        log.debug("Assigning principal: " + username);
        log.debug("Assigning authorities: " + roleList);
        return new UsernamePasswordAuthenticationToken(username, null, roleList.stream().map(item -> new SimpleGrantedAuthority(item.name())).toList());

    }



}
