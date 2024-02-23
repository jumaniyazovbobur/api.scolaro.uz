package api.scolaro.uz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * @author 'Mukhtarov Sarvarbek' on 23.02.2024
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Testcontainers
public class TestContainer {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withUsername("scolaro")
                    .withDatabaseName("scolaro_db")
                    .withPassword("scolaro_pass");

    @Test
    void canReadyPostgresSQL() {
        Assertions.assertTrue(postgreSQLContainer.isRunning());
        Assertions.assertTrue(postgreSQLContainer.isCreated());
    }
}
