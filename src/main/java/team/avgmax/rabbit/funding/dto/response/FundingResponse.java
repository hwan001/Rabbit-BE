package team.avgmax.rabbit.funding.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

import java.math.BigDecimal;

import team.avgmax.rabbit.funding.entity.Funding;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FundingResponse(
    String fundingId,
    String fundBunnyId,
    String userId,
    BigDecimal quantity,
    BigDecimal myHoldingQuantity
) {
    public static FundingResponse from(Funding funding, BigDecimal myHoldingQuantity) {
        return FundingResponse.builder()
                .fundingId(funding.getId())
                .fundBunnyId(funding.getFundBunny().getId())
                .userId(funding.getUser().getId())
                .quantity(funding.getQuantity())
                .myHoldingQuantity(myHoldingQuantity)
                .build();
    }
}
