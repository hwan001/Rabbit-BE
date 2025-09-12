package team.avgmax.rabbit.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"))
                .defaultHeader("Content-Type", "application/json");
    }
}
