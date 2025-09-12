package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.dto.request.CertificationRequest;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification extends BaseTime {

    @Id
    @Column(name = "certification_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    private String certificateUrl;

    private String name;

    private String ca;

    private LocalDate cdate;

    private Integer priority;

    public static Certification create(CertificationRequest request) {
        return Certification.builder()
                .certificateUrl(request.certificateUrl())
                .name(request.name())
                .ca(request.ca())
                .cdate(request.cdate())
                .build();
    }
}
