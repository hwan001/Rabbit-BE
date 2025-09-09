package team.avgmax.rabbit.funding.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;

import team.avgmax.rabbit.bunny.entity.enums.BunnyType;
import team.avgmax.rabbit.funding.entity.FundBunny;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FundBunnyResponse(
    String fundBunnyId,
    String userId,
    String bunnyName,
    BunnyType bunnyType,
    BigDecimal marketCap,
    BigDecimal totalSupply,
    BigDecimal price,
    LocalDateTime createdAt
) {
    public static FundBunnyResponse from(FundBunny fundBunny) {
        return FundBunnyResponse.builder()
                .fundBunnyId(fundBunny.getId())
                .userId(fundBunny.getUser().getId())
                .bunnyName(fundBunny.getBunnyName())
                .bunnyType(fundBunny.getType())
                .marketCap(fundBunny.getType().getMarketCap())
                .totalSupply(fundBunny.getType().getTotalSupply())
                .price(fundBunny.getType().getPrice())
                .createdAt(fundBunny.getCreatedAt())
                .build();
    }
}
