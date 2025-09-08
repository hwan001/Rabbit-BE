package team.avgmax.rabbit.funding.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FundBunnyListResponse(
    long totalCount,
    List<FundBunnyResponse> fundBunnies
) {
    public static FundBunnyListResponse of(List<FundBunnyResponse> fundBunnies) {
        return FundBunnyListResponse.builder()
                .totalCount(fundBunnies.size())
                .fundBunnies(fundBunnies)
                .build();
    }
}
