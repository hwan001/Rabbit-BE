package team.avgmax.rabbit.user.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CertificationRequest(
    String certificateUrl,
    String name,
    String ca,
    LocalDate cdate
) { 
}
