package team.avgmax.rabbit.ai.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import team.avgmax.rabbit.ai.service.StockService;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock/forecast")
    public Mono<Integer> forecast(@RequestParam String text) {
        return stockService.analyzeStockNews(text);
    }
}
