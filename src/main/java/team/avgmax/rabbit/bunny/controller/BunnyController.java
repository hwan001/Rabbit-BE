package team.avgmax.rabbit.bunny.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import team.avgmax.rabbit.bunny.dto.response.FetchBunnyResponse;
import team.avgmax.rabbit.bunny.entity.enums.BunnyFilter;
import team.avgmax.rabbit.bunny.service.BunnyService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bunnies")
public class BunnyController {

    private final BunnyService bunnyService;

    // 버니 목록 조회
    @GetMapping
    public ResponseEntity<List<FetchBunnyResponse>> getBunnies(@RequestParam(required = false) String filter) {

        BunnyFilter bunnyFilter = BunnyFilter.fromValue(filter); // IllegalAccessException 발생 시 GlobalException 에서 처리
        List<FetchBunnyResponse> fetchBunnyResponses = bunnyService.getBunniesByFilter(bunnyFilter);

        return ResponseEntity.ok(fetchBunnyResponses);
    }
}
