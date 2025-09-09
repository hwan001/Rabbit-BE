package team.avgmax.rabbit.user.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import team.avgmax.rabbit.user.entity.Career;
import team.avgmax.rabbit.user.entity.enums.CareerStatus;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CareerResponse(
    String companyName,
    CareerStatus status,
    String position,
    LocalDate startDate,
    LocalDate endDate,
    String certificateUrl
) {
    public static CareerResponse from(Career career) {
        return CareerResponse.builder()
                .companyName(career.getCompanyName())
                .status(career.getStatus())
                .position(career.getPosition())
                .startDate(career.getStartDate())
                .endDate(career.getEndDate())
                .certificateUrl(career.getCertificateUrl())
                .build();
    }

    public static List<CareerResponse> from(List<Career> career) {
        return career.stream()
                .map(CareerResponse::from)
                .toList();
    }
}
