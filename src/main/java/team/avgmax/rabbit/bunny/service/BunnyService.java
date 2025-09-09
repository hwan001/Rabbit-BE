package team.avgmax.rabbit.bunny.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.bunny.dto.response.FetchBunnyResponse;
import team.avgmax.rabbit.bunny.entity.Bunny;
import team.avgmax.rabbit.bunny.entity.enums.BunnyFilter;
import team.avgmax.rabbit.bunny.repository.BunnyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BunnyService {

    private final BunnyRepository bunnyRepository;
    private static final long BADGE_COUNT = 3L;

    // 버니 목록 조회
    public List<FetchBunnyResponse> getBunniesByFilter(BunnyFilter filter) {

        List<Bunny> bunnies = switch (filter) {
            case ALL -> bunnyRepository.findBunniesWithBadgeCount(BADGE_COUNT); // 로켓 탑승한 버니들
            case LATEST -> bunnyRepository.findAllByOrderByCreatedAtDesc(); // GOT 탑승한 버니들
            case CAPITALIZATION -> bunnyRepository.findTop5ByOrderByMarketCapDesc(); // Top 5 버니들
        };

        return bunnies.stream()
                .map(FetchBunnyResponse::from)
                .toList();
    }
}
