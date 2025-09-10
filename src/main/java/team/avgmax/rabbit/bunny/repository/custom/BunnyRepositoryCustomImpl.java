package team.avgmax.rabbit.bunny.repository.custom;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.bunny.entity.Bunny;

import java.util.List;

import static team.avgmax.rabbit.bunny.entity.QBadge.badge;
import static team.avgmax.rabbit.bunny.entity.QBunny.bunny;

@RequiredArgsConstructor
public class BunnyRepositoryCustomImpl implements BunnyRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private static final long BADGE_COUNT = 3L;

    @Override
    public List<Bunny> findAllByPriorityGroupAndCreatedAt() {
        NumberExpression<Integer> priorityGroup = new CaseBuilder()
                .when(badge.count().eq(BADGE_COUNT)).then(0).otherwise(1);

        return queryFactory
                .select(bunny)
                .from(bunny)
                .leftJoin(badge).on(bunny.id.eq(badge.bunnyId))
                .groupBy((bunny.id))
                .orderBy(
                        priorityGroup.asc(),
                        bunny.createdAt.asc()
                )
                .fetch();
    }
}
