package team.avgmax.rabbit.funding.repository;

import team.avgmax.rabbit.funding.dto.data.UserFundingSummary;
import team.avgmax.rabbit.funding.entity.FundBunny;
import team.avgmax.rabbit.user.entity.PersonalUser;

import java.math.BigDecimal;
import java.util.List;

public interface FundingRepositoryCustom {
    List<UserFundingSummary> findUserFundingSummariesByFundBunnyOrderByQuantityDesc(FundBunny fundBunny);
    
    BigDecimal findTotalQuantityByUserAndFundBunny(PersonalUser user, FundBunny fundBunny);
}
