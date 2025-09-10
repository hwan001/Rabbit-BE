package team.avgmax.rabbit.funding.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import team.avgmax.rabbit.funding.service.FundingService;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.funding.dto.request.CreateFundingRequest;
import team.avgmax.rabbit.funding.dto.response.FundBunnyDetailResponse;
import team.avgmax.rabbit.funding.dto.response.FundBunnyListResponse;
import team.avgmax.rabbit.funding.dto.response.FundBunnyResponse;
import team.avgmax.rabbit.funding.controller.enums.FundBunnySortType;
import team.avgmax.rabbit.funding.dto.response.FundingResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    // 버니 이름 중복 체크
    @RequestMapping(value = "/fund-bunnies/{bunnyName}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkBunnyName(@AuthenticationPrincipal Jwt jwt, @PathVariable String bunnyName) {
        log.info("HEAD 버니 이름 중복 체크: bunnyName={}", bunnyName);
        boolean isDuplicate = fundingService.checkDuplicateBunnyName(bunnyName);
        return isDuplicate ? ResponseEntity.status(HttpStatus.CONFLICT).build() : ResponseEntity.ok().build();
    }

    // 버니 심사 요청
    @PostMapping("/fund-bunnies")
    public ResponseEntity<FundBunnyResponse> createFundBunnies(@AuthenticationPrincipal Jwt jwt, @RequestBody CreateFundBunnyRequest request) {
        String userId = jwt.getSubject();
        log.info("POST 버니 심사 요청: bunnyName={}", request.bunnyName());
        return ResponseEntity.ok(fundingService.createFundBunny(request, userId));
    }

    // 펀딩 중인 버니 목록 조회
    @GetMapping("/fund-bunnies")
    public ResponseEntity<FundBunnyListResponse> getFundBunnyList(@RequestParam(defaultValue = "newest") String sortType, Pageable pageable) {
        log.info("GET 펀딩 중인 버니 목록 조회: sort={}", sortType);
        return ResponseEntity.ok(fundingService.getFundBunnyList(FundBunnySortType.fromValue(sortType), pageable));
    }

    // 펀딩 중인 버니 상세 조회
    @GetMapping("/fund-bunnies/{fundBunnyId}")
    public ResponseEntity<FundBunnyDetailResponse> getFundBunnyDetail(@AuthenticationPrincipal Jwt jwt, @PathVariable String fundBunnyId) {
        String userId = jwt.getSubject();
        log.info("GET 펀딩 중인 버니 상세 조회: fundBunnyId={}", fundBunnyId);
        return ResponseEntity.ok(fundingService.getFundBunnyDetail(fundBunnyId, userId));
    }

    // 펀딩 등록
    @PostMapping("/fund-bunnies/{fundBunnyId}/fundings")
    public ResponseEntity<FundingResponse> postFunding(@AuthenticationPrincipal Jwt jwt, @PathVariable String fundBunnyId, @RequestBody CreateFundingRequest request) {
        String userId = jwt.getSubject();
        log.info("POST 펀딩 등록: fundBunnyId={}", fundBunnyId);
        return fundingService.createFunding(fundBunnyId, userId, request)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
