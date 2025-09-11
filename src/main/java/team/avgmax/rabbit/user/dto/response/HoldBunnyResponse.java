package team.avgmax.rabbit.user.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record HoldBunnyResponse(
    String bunnyId,
    String bunnyName,
    BigDecimal holdQuantity,
    BigDecimal totalBuyAmount
) {}
