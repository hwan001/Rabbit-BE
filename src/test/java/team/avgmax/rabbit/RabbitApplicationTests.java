package team.avgmax.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import team.avgmax.rabbit.global.config.SecurityConfig;

@SpringBootTest
@Import(SecurityConfig.class)
class RabbitApplicationTests { 
    @Test void contextLoads() {}
}