package team.avgmax.rabbit.user.dto.request;

import java.time.LocalDate;

import team.avgmax.rabbit.user.entity.enums.CareerStatus;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CareerRequest(
    String companyName,
    CareerStatus status,
    String position,
    LocalDate startDate,
    LocalDate endDate,
    String certificateUrl
) {
}
