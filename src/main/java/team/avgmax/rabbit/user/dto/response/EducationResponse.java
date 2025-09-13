package team.avgmax.rabbit.user.dto.response;

import java.time.LocalDate;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import team.avgmax.rabbit.user.entity.Education;
import team.avgmax.rabbit.user.entity.enums.EducationStatus;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record EducationResponse(
    String educationId,
    String schoolName,
    EducationStatus status,
    String major,
    LocalDate startDate,
    LocalDate endDate,
    String certificateUrl
) {
    public static EducationResponse from(Education education) {
        return EducationResponse.builder()
                .educationId(education.getId())
                .schoolName(education.getSchoolName())
                .status(education.getStatus())
                .major(education.getMajor())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .certificateUrl(education.getCertificateUrl())
                .build();
    }

    public static List<EducationResponse> from(List<Education> education) {
        return education.stream()
                .map(EducationResponse::from)
                .toList();
    }
}
