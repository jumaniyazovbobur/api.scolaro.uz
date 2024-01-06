package api.scolaro.uz.config.restTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Configuration
public class RestTemplateConfig {
    @Value("${firebase.api.token}")
    private String token;

    @Bean(name = "RestTemplateFirebase")
    public RestTemplate restTemplateFirebase() {
        return new RestTemplateBuilder()
                .rootUri("https://fcm.googleapis.com")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", token)
                .build();
    }
}