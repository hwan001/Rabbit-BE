package team.avgmax.rabbit.bunny.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.bunny.entity.Bunny;

public interface BunnyRepository extends JpaRepository<Bunny, String> {
}
