package api.scolaro.uz.config.webSocket;

/**
 * @author Admin on 23.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.config.webSocket
 * @contact @sarvargo
 */

import api.scolaro.uz.config.details.CustomUserDetails;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.repository.consulting.ConsultingProfileRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
@Slf4j
public class WebSocketConnectEventListener implements ApplicationListener<SessionConnectedEvent> {
    private final ConsultingProfileRepository consultingProfileRepository;
    private final ProfileRepository profileRepository;

    public WebSocketConnectEventListener(ConsultingProfileRepository consultingProfileRepository,
                                         ProfileRepository profileRepository) {
        this.consultingProfileRepository = consultingProfileRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Extract session ID or other information about the connected user
        String sessionId = headerAccessor.getSessionId();


        // Perform any necessary actions or logging
        log.info("User connected. Session ID:{}", sessionId);
    }
}