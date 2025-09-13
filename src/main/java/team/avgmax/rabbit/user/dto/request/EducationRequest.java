package team.avgmax.rabbit.user.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import team.avgmax.rabbit.user.entity.enums.EducationStatus;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EducationRequest(
    String schoolName,
    EducationStatus status,
    String major,
    LocalDate startDate,
    LocalDate endDate,
    String certificateUrl
) { 
}
