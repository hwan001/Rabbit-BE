package team.avgmax.rabbit.bunny.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import team.avgmax.rabbit.bunny.dto.response.FetchBunnyResponse;
import team.avgmax.rabbit.bunny.entity.Badge;
import team.avgmax.rabbit.bunny.entity.Bunny;
import team.avgmax.rabbit.bunny.entity.enums.BunnyFilter;
import team.avgmax.rabbit.bunny.exception.BunnyError;
import team.avgmax.rabbit.bunny.exception.BunnyException;
import team.avgmax.rabbit.bunny.repository.BadgeRepository;
import team.avgmax.rabbit.bunny.repository.BunnyRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BunnyService {

    private final BunnyRepository bunnyRepository;
    private final BadgeRepository badgeRepository;

    // 버니 목록 조회
    @Transactional(readOnly = true) // 읽기 전용
    public List<FetchBunnyResponse> getBunniesByFilter(BunnyFilter filter) {
        List<Bunny> bunnies = switch (filter) {
            case ALL -> bunnyRepository.findAllByPriorityGroupAndCreatedAt(); // 로켓 탑승한 버니들
            case LATEST -> bunnyRepository.findAllByOrderByCreatedAtDesc(); // GOT 탑승한 버니들
            case CAPITALIZATION -> bunnyRepository.findTop5ByOrderByMarketCapDesc(); // Top 5 버니들
        };

        if (bunnies.isEmpty()) {
            log.debug("No bunnies found for filter={}", filter);
        }

        List<String> bunnyIds = bunnies.stream().map(Bunny::getId).toList();

        Map<String, List<Badge>> badgesByBunnyId = badgeRepository.findAllByBunnyIdIn(bunnyIds).stream()
                .collect(Collectors.groupingBy(Badge::getBunnyId));

        return bunnies.stream()
                .map(bunny -> {
                    List<Badge> badges = badgesByBunnyId.getOrDefault(bunny.getId(), Collections.emptyList());
                    return FetchBunnyResponse.from(bunny,badges);
                })
                .toList();
    }

    // 버니 상세 조회
    @Transactional(readOnly = true)
    public FetchBunnyResponse getBunnyByName(String bunnyName) {
        Bunny bunny = bunnyRepository.findByBunnyName(bunnyName)
                .orElseThrow(() -> new BunnyException(BunnyError.BUNNY_NOT_FOUND));

        List<Badge> badges = badgeRepository.findAllByBunnyId(bunny.getId());

        log.debug("Found bunny id={} name={}", bunny.getId(), bunny.getBunnyName());

        return FetchBunnyResponse.from(bunny, badges);
    }
}
