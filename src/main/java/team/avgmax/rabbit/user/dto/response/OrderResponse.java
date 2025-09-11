package team.avgmax.rabbit.user.dto.response;

import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record OrderResponse(
    String orderId,
    String bunnyName,
    String bunnyId,
    BigDecimal quantity,
    BigDecimal unitPrice,
    String orderType
) {}
