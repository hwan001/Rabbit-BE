package team.avgmax.rabbit.funding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.avgmax.rabbit.funding.entity.FundBunny;

public interface FundBunnyRepositoryCustom {
    Page<FundBunny> findAllByOrderByCollectedAmountDesc(Pageable pageable);
    Page<FundBunny> findAllByOrderByCollectedAmountAsc(Pageable pageable);
    long countByEndAtWithin24Hours();
}
