package api.scolaro.uz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
