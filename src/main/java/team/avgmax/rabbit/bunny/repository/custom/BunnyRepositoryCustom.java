package team.avgmax.rabbit.bunny.repository.custom;

import team.avgmax.rabbit.bunny.entity.Bunny;

import java.util.List;

public interface BunnyRepositoryCustom {

    List<Bunny> findBunniesWithBadgeCount(long badgeCount);
}
