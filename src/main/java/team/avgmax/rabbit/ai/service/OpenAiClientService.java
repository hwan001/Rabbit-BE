package team.avgmax.rabbit.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenAiClientService {

    private final WebClient webClient;

    public OpenAiClientService(WebClient.Builder builder,
                               @Value("${openai.api-key}") String apiKey,
                               @Value("${openai.base-url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<Integer> getScoreFromPrompt(String prompt) {
        String body = """
        {
          "model": "gpt-4o-mini",
          "messages": [
            {"role": "system", "content": "너는 금융 애널리스트 역할을 한다. 입력된 텍스트를 보고 주가 전망을 0~100 사이 점수로 표현해라. 0=매우 부정적, 50=중립, 100=매우 긍정적"},
            {"role": "user", "content": "%s"}
          ],
          "response_format": {
            "type": "json_schema",
            "json_schema": {
              "name": "stock_forecast",
              "schema": {
                "type": "object",
                "properties": {
                  "score": { "type": "number", "minimum": 0, "maximum": 100 }
                },
                "required": ["score"]
              }
            }
          }
        }
        """.formatted(prompt);

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .map(resp -> {
                    // 간단 파싱: "score": 73
                    int idx = resp.indexOf("\"score\":");
                    if (idx > 0) {
                        String sub = resp.substring(idx + 8).trim();
                        return Integer.parseInt(sub.replaceAll("[^0-9]", ""));
                    }
                    return 50;
                });
    }
}
