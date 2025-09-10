package team.avgmax.rabbit.funding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.funding.entity.Funding;
import team.avgmax.rabbit.funding.entity.FundBunny;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, String>, FundingRepositoryCustom {
    List<Funding> findByFundBunny(FundBunny fundBunny);
}
