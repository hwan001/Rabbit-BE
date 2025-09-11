package team.avgmax.rabbit.user.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.user.dto.response.HoldBunniesResponse;
import team.avgmax.rabbit.user.dto.response.HoldBunnyResponse;
import team.avgmax.rabbit.user.entity.QHoldBunny;

@Repository
@RequiredArgsConstructor
public class HoldBunnyRepositoryCustomImpl implements HoldBunnyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public HoldBunniesResponse findHoldbunniesByUserId(String personalUserId) {
        QHoldBunny holdBunny = QHoldBunny.holdBunny;

        List<HoldBunnyResponse> holdBunnies = queryFactory
                .select(Projections.constructor(HoldBunnyResponse.class,
                        holdBunny.bunny.id,
                        holdBunny.bunny.bunnyName,
                        holdBunny.holdQuantity,
                        holdBunny.totalBuyAmount
                        ))
                .from(holdBunny)
                .where(holdBunny.holder.id.eq(personalUserId))
                .fetch();

        return HoldBunniesResponse.builder()
                .holdBunnies(holdBunnies)
                .build();
    }

}
