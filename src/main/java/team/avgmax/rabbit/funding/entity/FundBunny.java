package team.avgmax.rabbit.funding.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.global.entity.BaseTime;
import team.avgmax.rabbit.global.util.UlidGenerator;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.bunny.entity.enums.BunnyType;
import team.avgmax.rabbit.funding.entity.enums.FundBunnyStatus;

import java.math.BigDecimal;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FundBunny extends BaseTime {

    @Id
    @Column(name = "fund_bunny_id", length = 26, updatable = false, nullable = false)
    @Builder.Default
    private String id = UlidGenerator.generateMonotonic();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private PersonalUser user;

    @Column(unique = true, nullable = false)
    private String bunnyName;

    @Enumerated(EnumType.STRING)
    private BunnyType type;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FundBunnyStatus status = FundBunnyStatus.ONGOING;

    @Builder.Default
    private BigDecimal backerCount = BigDecimal.ZERO;

    public static FundBunny create(CreateFundBunnyRequest request, PersonalUser user) {
        return FundBunny.builder()
                .user(user)
                .bunnyName(request.bunnyName())
                .type(request.bunnyType())
                .build();
    }
}
