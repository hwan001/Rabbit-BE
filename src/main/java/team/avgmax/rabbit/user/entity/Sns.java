package team.avgmax.rabbit.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sns extends BaseTime {

    @Id
    @Column(name = "sns_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    private String url;

    private String type;

    private Integer priority;
}
