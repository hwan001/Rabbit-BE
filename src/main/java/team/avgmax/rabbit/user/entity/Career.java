package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.util.UlidGenerator;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private PersonalUser user;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private CareerStatus status;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    private String certificateUrl;

    private Integer priority;
}
