package team.avgmax.rabbit.funding.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FundBunnyListResponse(
    long size,
    List<FundBunnyResponse> fundBunnies
) {
    public static FundBunnyListResponse from(List<FundBunnyResponse> fundBunnies) {
        return FundBunnyListResponse.builder()
                .size(fundBunnies.size())
                .fundBunnies(fundBunnies)
                .build();
    }
}
