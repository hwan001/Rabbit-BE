package team.avgmax.rabbit.funding.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import team.avgmax.rabbit.funding.service.FundingService;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.funding.dto.response.FundBunnyListResponse;
import team.avgmax.rabbit.funding.dto.response.FundBunnyResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    // 버니 이름 중복 체크
    @RequestMapping(value = "/fund-bunnies/{bunnyName}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkBunnyName(@PathVariable String bunnyName) {
        boolean isDuplicate = fundingService.checkDuplicateBunnyName(bunnyName);
        return isDuplicate ? ResponseEntity.status(HttpStatus.CONFLICT).build() : ResponseEntity.ok().build();
    }

    // 버니 심사 요청
    @PostMapping("/fund-bunnies")
    public ResponseEntity<FundBunnyResponse> createFundBunnies(@RequestBody CreateFundBunnyRequest request) {
        // 임시 user
        String userId = "01HZX1T5F5QW4Z8E7T9C6K3M1N";
        return ResponseEntity.ok(fundingService.createFundBunny(request, userId));
    }

    // // 펀딩 중인 버니 목록 조회
    // @GetMapping("/fund-bunnies")
    // public ResponseEntity<FundBunnyListResponse> getFundBunnyList(@RequestParam(required = false) Boolean endingSoon) {
    //     return ResponseEntity.ok(fundingService.getFundBunnyList(endingSoon));
    // }

    // // 펀딩 중인 버니 상세 조회
    // @GetMapping("/fund-bunnies/{fundBunnyId}")
    // public ResponseEntity<FundBunnyResponse> getFundBunny(@PathVariable String fundBunnyId) {
    //     return ResponseEntity.ok(fundingService.getFundBunny(fundBunnyId));
    // }
}
