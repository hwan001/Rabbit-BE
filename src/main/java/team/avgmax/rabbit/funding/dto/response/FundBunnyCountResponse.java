package team.avgmax.rabbit.funding.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FundBunnyCountResponse(
    long listedBunnyCount,
    long fundBunnyCount,
    long closingSoonBunnyCount
) {
    public static FundBunnyCountResponse of(long listedBunnyCount, long fundBunnyCount, long closingSoonBunnyCount) {
        return FundBunnyCountResponse.builder()
                .listedBunnyCount(listedBunnyCount)
                .fundBunnyCount(fundBunnyCount)
                .closingSoonBunnyCount(closingSoonBunnyCount)
                .build();
    }
}