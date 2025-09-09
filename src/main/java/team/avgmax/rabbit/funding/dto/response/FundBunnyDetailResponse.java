package team.avgmax.rabbit.funding.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import team.avgmax.rabbit.user.dto.response.SpecResponse;
import team.avgmax.rabbit.funding.entity.FundBunny;
import team.avgmax.rabbit.user.entity.PersonalUser;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FundBunnyDetailResponse(
        String bunnyName,
        String bunnyType,
        BigDecimal targetBny,
        BigDecimal collectedBny,
        BigDecimal remainingBny,
        ShareStatusResponse shareStatus,
        BigDecimal myAccountBny,
        BigDecimal myAccountC,
        SpecResponse spec
    ) {
    public static FundBunnyDetailResponse from(FundBunny fundBunny, PersonalUser user) {
        return FundBunnyDetailResponse.builder()
                .bunnyName(fundBunny.getBunnyName())
                .bunnyType(fundBunny.getType().name())
                .targetBny(fundBunny.getType().getTotalSupply())
                .collectedBny(null) // 펀딩 등록 구현 이후 개발
                .remainingBny(null) // 펀딩 등록 구현 이후 개발
                .shareStatus(null) // 펀딩 등록 구현 이후 개발
                .myAccountBny(user.getCarrot().divide(fundBunny.getType().getPrice(), RoundingMode.DOWN)) // 보유 캐럿 조회 구현 이후 개발
                .myAccountC(user.getCarrot()) // 보유 캐럿 조회 구현 이후 개발
                .spec(SpecResponse.from(fundBunny.getUser())) // 나의 정보 수정 구현 이후 개발
                .build();
    }
}
