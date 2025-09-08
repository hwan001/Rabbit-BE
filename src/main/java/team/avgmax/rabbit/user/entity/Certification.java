package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification extends BaseTime {

    @Id
    @Column(name = "certification_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private PersonalUser user;

    private String certificateUrl;

    private String name;

    private String ca;

    private LocalDate cdate;

    private Integer priority;
}
