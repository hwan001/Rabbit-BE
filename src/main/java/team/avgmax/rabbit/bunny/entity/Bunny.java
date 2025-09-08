package team.avgmax.rabbit.bunny.entity;

import jakarta.persistence.*;
import lombok.*;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.bunny.entity.enums.BunnyType;
import team.avgmax.rabbit.bunny.entity.enums.DeveloperType;

import java.math.BigDecimal;

import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.entity.enums.Position;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bunny extends BaseTime {

    @Id
    @Column(name = "bunny_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private PersonalUser user;

    @Column(unique = true, nullable = false)
    private String bunnyName;

    @Enumerated(EnumType.STRING)
    private DeveloperType developerType;

    @Enumerated(EnumType.STRING)
    private BunnyType bunnyType;
   
    @Enumerated(EnumType.STRING)
    private Position position;

    private BigDecimal currentPrice; 

    private BigDecimal closingPrice;

    // 5가지 성장 지표 추후 추가
    
    private String aiReview;

    private String aiFeedback;
}
