package api.scolaro.uz.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;

@Service
@Slf4j
public class CommandLineRunnerService implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();

        firebaseInit();
    }

    private void firebaseInit() {
        try {
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.warn("Couldn't initialize firebase");
        }

    }
}
