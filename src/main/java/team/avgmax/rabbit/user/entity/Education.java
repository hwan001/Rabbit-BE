package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.dto.request.EducationRequest;
import team.avgmax.rabbit.user.entity.enums.EducationStatus;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Education extends BaseTime {

    @Id
    @Column(name = "education_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    private String schoolName;

    @Enumerated(EnumType.STRING)
    private EducationStatus status;

    private String major;
    
    private LocalDate startDate;

    private LocalDate endDate;

    private String certificateUrl;

    private Integer priority;

    public static Education create(EducationRequest request) {
        return Education.builder()
                .schoolName(request.schoolName())
                .status(request.status())
                .major(request.major())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .certificateUrl(request.certificateUrl())
                .build();
    }
}
