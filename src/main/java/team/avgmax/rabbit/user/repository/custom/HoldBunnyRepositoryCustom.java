package team.avgmax.rabbit.user.repository.custom;

import team.avgmax.rabbit.user.dto.response.HoldBunniesResponse;

public interface HoldBunnyRepositoryCustom {
    HoldBunniesResponse findHoldbunniesByUserId(String personalUserId);
}