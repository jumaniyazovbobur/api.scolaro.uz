package api.scolaro.uz;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author 'Mukhtarov Sarvarbek' on 23.02.2024
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public class TestContainer extends AbstractTestContainers {

    @Test
    void canReadyPostgresSQL() {
        Assertions.assertTrue(postgreSQLContainer.isRunning());
        Assertions.assertTrue(postgreSQLContainer.isCreated());
    }
}
