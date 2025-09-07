package team.avgmax.rabbit.funding.entity;

import jakarta.persistence.*;
import lombok.*;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;

import java.math.BigDecimal;

import team.avgmax.rabbit.user.entity.PersonalUser;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseTime {

    @Id
    @Column(name = "funding_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_bunny_id", nullable = false)
    private FundBunny fundBunny;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private PersonalUser user;

    private BigDecimal quantity;
}
