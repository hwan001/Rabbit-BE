package team.avgmax.rabbit.bunny.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.bunny.entity.Bunny;

import java.util.List;

import static team.avgmax.rabbit.bunny.entity.QBadge.badge;
import static team.avgmax.rabbit.bunny.entity.QBunny.bunny;

@RequiredArgsConstructor
public class BunnyRepositoryCustomImpl implements BunnyRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Bunny> findBunniesWithBadgeCount(long badgeCount) {
        return queryFactory
                .select(bunny)
                .from(bunny)
                .join(badge).on(bunny.user.id.eq(badge.userId))
                .groupBy(bunny.id)
                .having(bunny.id.count().eq(badgeCount))
                .orderBy(bunny.createdAt.asc())
                .fetch();
    }
}
