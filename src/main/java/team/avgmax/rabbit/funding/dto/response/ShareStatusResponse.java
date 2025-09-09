package team.avgmax.rabbit.funding.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ShareStatusResponse(
        Double top1,
        Double top2,
        Double top3,
        Double others,
        Double remaining
) {
}
