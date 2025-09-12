package team.avgmax.rabbit.ai.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockService {

    private final OpenAiClientService openAiClientService;

    public StockService(OpenAiClientService openAiClientService) {
        this.openAiClientService = openAiClientService;
    }

    public Mono<Integer> analyzeStockNews(String news) {
        return openAiClientService.getScoreFromPrompt(news);
    }
}
