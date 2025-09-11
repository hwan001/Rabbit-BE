package team.avgmax.rabbit.funding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.funding.entity.FundBunny;

public interface FundBunnyRepository extends JpaRepository<FundBunny, String>, FundBunnyRepositoryCustom {
    boolean existsByBunnyName(String bunnyName);
    Page<FundBunny> findAllByOrderByCreatedAtAsc(Pageable pageable);
    Page<FundBunny> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
