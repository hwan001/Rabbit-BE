package team.avgmax.rabbit.funding.controller.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FundBunnySortType {
    MOST_INVESTED("mostInvested"),   // 투자 많은 순
    LEAST_INVESTED("leastInvested"),  // 투자 적은 순
    OLDEST("oldest"),          // 오래된 순
    NEWEST("newest");          // 최신순

    private final String value;

    public static FundBunnySortType fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort type: " + value));
    }
}

