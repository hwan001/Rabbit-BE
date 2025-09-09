package team.avgmax.rabbit.bunny.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.bunny.entity.BunnyHistory;

public interface BunnyHistoryRepository extends JpaRepository<BunnyHistory, String> {
}
