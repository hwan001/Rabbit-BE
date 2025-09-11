package team.avgmax.rabbit.user.dto.request;

import java.time.LocalDate;

import team.avgmax.rabbit.user.entity.enums.EducationStatus;

public record EducationRequest(
    String schoolName,
    EducationStatus status,
    String major,
    LocalDate startDate,
    LocalDate endDate,
    String certificateUrl,
    Integer priority
) {

}