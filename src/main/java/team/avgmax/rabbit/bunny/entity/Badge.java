package team.avgmax.rabbit.bunny.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.bunny.entity.enums.BadgeImg;
import team.avgmax.rabbit.bunny.entity.id.BadgeId;
import team.avgmax.rabbit.global.entity.BaseTime;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BadgeId.class)
public class Badge extends BaseTime {

    @Id
    @Column(name = "bunny_id", length = 26, nullable = false)
    private String bunnyId;

    @Id
    @Column(name = "user_id", length = 26, nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    private BadgeImg badgeImg;
}
