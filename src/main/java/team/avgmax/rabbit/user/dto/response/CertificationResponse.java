package team.avgmax.rabbit.user.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import team.avgmax.rabbit.user.entity.Certification;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CertificationResponse(
    String certificationId,
    String certificateUrl,
    String name,
    String ca,
    LocalDate cdate
) {
    public static CertificationResponse from(Certification certification) {
        return CertificationResponse.builder()
                .certificationId(certification.getId())
                .certificateUrl(certification.getCertificateUrl())
                .name(certification.getName())
                .ca(certification.getCa())
                .cdate(certification.getCdate())
                .build();
    }

    public static List<CertificationResponse> from(List<Certification> certification) {
        return certification.stream()
                .map(CertificationResponse::from)
                .toList();
    }
}
