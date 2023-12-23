package api.scolaro.uz.config.webSocket;

/**
 * @author Admin on 23.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.config.webSocket
 * @contact @sarvargo
 */
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class WebSocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Extract session ID or other information about the disconnected user
        String sessionId = headerAccessor.getSessionId();
        Principal user = headerAccessor.getUser();

        // Perform any necessary cleanup or logging
        System.out.println("User disconnected. Session ID: " + sessionId);
    }
}