package api.scolaro.uz;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

/**
 * @author Admin on 23.02.2024
 * @project api.scolaro.uz
 * @package api.scolaro.uz
 * @contact @sarvargo
 */
@Testcontainers
public abstract class AbstractTestContainers {
    @BeforeAll
    static void beforeLAll() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                        postgreSQLContainer.getJdbcUrl(),
                        postgreSQLContainer.getUsername(),
                        postgreSQLContainer.getPassword()
                )
                .load();
        flyway.migrate();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15")
                    .withUsername("scolaro")
                    .withDatabaseName("scolaro_db")
                    .withPassword("scolaro_pass");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    protected static DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .build();
    }
}