package team.avgmax.rabbit.funding.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import team.avgmax.rabbit.funding.service.FundingService;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.funding.dto.response.FundBunnyResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @PostMapping("/fund-bunnies")
    public ResponseEntity<FundBunnyResponse> createFundBunnies(@RequestBody CreateFundBunnyRequest request) {
        // 임시 user
        String userId = "01HZX1T5F5QW4Z8E7T9C6K3M1N";
        return ResponseEntity.ok(fundingService.createFundBunny(request, userId));
    }
}
