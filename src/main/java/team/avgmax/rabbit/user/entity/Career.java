package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.dto.request.CareerRequest;
import team.avgmax.rabbit.user.entity.enums.CareerStatus;

import team.avgmax.rabbit.global.entity.BaseTime;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career extends BaseTime {

    @Id
    @Column(name = "career_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    private String companyName;

    @Enumerated(EnumType.STRING)
    private CareerStatus status;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    private String certificateUrl;
    
    private Integer priority;

    public static Career create(CareerRequest request) {
        return Career.builder()
                .companyName(request.companyName())
                .status(request.status())
                .position(request.position())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .certificateUrl(request.certificateUrl())
                .build();
    }
}
