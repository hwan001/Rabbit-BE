package team.avgmax.rabbit.bunny.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;
import lombok.experimental.SuperBuilder;
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

    private String badgeImg;
}
