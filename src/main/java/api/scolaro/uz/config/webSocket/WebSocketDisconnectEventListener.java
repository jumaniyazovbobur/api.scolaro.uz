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
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
@Slf4j
public class WebSocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    private final ConsultingProfileRepository consultingProfileRepository;
    private final ProfileRepository profileRepository;

    public WebSocketDisconnectEventListener(ConsultingProfileRepository consultingProfileRepository,
                                            ProfileRepository profileRepository) {
        this.consultingProfileRepository = consultingProfileRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Extract session ID or other information about the disconnected user
        String sessionId = headerAccessor.getSessionId();

        // Perform any necessary cleanup or logging
        log.info("User disconnected. Session ID: {}", sessionId);
    }
}