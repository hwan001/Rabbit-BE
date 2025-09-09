package team.avgmax.rabbit.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.funding.entity.FundBunny;

public interface FundBunnyRepository extends JpaRepository<FundBunny, String> {
    boolean existsByBunnyName(String bunnyName);
}
