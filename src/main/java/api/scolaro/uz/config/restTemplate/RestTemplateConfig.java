package api.scolaro.uz.config.restTemplate;

import api.scolaro.uz.service.notification.NotificationService;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Configuration
public class RestTemplateConfig {
    @Value("${firebase.api.url}")
    private String url;

    @Bean
    public RestTemplate restTemplateFirebase() {
        return new RestTemplate();
    }
}