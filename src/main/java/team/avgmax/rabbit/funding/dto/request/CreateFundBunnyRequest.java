package team.avgmax.rabbit.funding.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import team.avgmax.rabbit.bunny.entity.enums.BunnyType;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateFundBunnyRequest(
    String bunnyName,
    BunnyType bunnyType
) {
}
