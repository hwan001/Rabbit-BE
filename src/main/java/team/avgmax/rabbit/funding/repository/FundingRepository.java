package team.avgmax.rabbit.funding.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import team.avgmax.rabbit.funding.entity.Funding;

public interface FundingRepository extends JpaRepository<Funding, String> {
}
