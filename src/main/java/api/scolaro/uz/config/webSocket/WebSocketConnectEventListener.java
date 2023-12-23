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
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class WebSocketConnectEventListener implements ApplicationListener<SessionConnectedEvent> {

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Extract session ID or other information about the connected user
        String sessionId = headerAccessor.getSessionId();

        // Perform any necessary actions or logging
        System.out.println("User connected. Session ID: " + sessionId);
    }
}