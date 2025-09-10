package team.avgmax.rabbit.funding.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;

import java.math.BigDecimal;

import team.avgmax.rabbit.funding.dto.request.CreateFundingRequest;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.entity.HoldBunny;
import team.avgmax.rabbit.bunny.entity.Bunny;

@Entity
@Getter
@SuperBuilder
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

    @Column(precision = 30)
    private BigDecimal quantity;

    public static Funding create(FundBunny fundBunny, PersonalUser user, CreateFundingRequest request) {
        return Funding.builder()
                .fundBunny(fundBunny)
                .user(user)
                .quantity(request.fundBny())
                .build();
    }

    public HoldBunny convertToHoldBunny(Bunny bunny) {
        return HoldBunny.create(bunny, this);
    }
}
