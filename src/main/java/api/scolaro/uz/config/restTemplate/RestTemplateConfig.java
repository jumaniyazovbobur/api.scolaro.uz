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

    @Bean(name = "RestTemplateFirebase")
    public RestTemplate restTemplateFirebase() {
        return new RestTemplateBuilder()
                .rootUri(url)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer %s".formatted(getAccessToken()))
                .build();
    }

    private static String getAccessToken() {
        GoogleCredentials googleCredentials;
        try {
            ClassLoader classLoader = NotificationService.class.getClassLoader();
            try (InputStream inputStream = classLoader.getResourceAsStream("firebase/scolaro-a0430-firebase-adminsdk-d4ooj-65d7fbb8a1.json")) {

                if (inputStream == null) throw new IllegalArgumentException("File not found! Check the file path.");

                googleCredentials = GoogleCredentials
                        .fromStream(inputStream)
                        .createScoped(List.of("https://www.googleapis.com/auth/firebase.messaging"));

                googleCredentials.refresh();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return googleCredentials.getAccessToken().getTokenValue();
    }
}