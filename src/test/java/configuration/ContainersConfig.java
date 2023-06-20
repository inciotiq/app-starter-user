package configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class ContainersConfig {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15.2-alpine");
    }

//    @Bean
//    @ServiceConnection
//    public KafkaContainer kafkaContainer() {
//        return new KafkaContainer(
//                DockerImageName.parse("confluentinc/cp-kafka:7.2.1"));
//    }
}