package team.avgmax.rabbit.funding.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.funding.dto.response.FundBunnyDetailResponse;
import team.avgmax.rabbit.funding.dto.response.FundBunnyResponse;
import team.avgmax.rabbit.funding.entity.FundBunny;
import team.avgmax.rabbit.funding.exception.FundingError;
import team.avgmax.rabbit.funding.exception.FundingException;
import team.avgmax.rabbit.bunny.repository.BunnyRepository;
import team.avgmax.rabbit.funding.repository.FundBunnyRepository;
import team.avgmax.rabbit.user.service.UserService;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.bunny.entity.enums.BunnyType;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final UserService userService;

    private final FundBunnyRepository fundBunnyRepository;
    private final BunnyRepository bunnyRepository;

    @Transactional
    public FundBunnyResponse createFundBunny(CreateFundBunnyRequest request, String userId) {
        validateBunnyName(request.bunnyName());
        validateBunnyType(request.bunnyType());
        if (checkDuplicateBunnyName(request.bunnyName())) {
            throw new FundingException(FundingError.BUNNY_NAME_DUPLICATE);
        }
        
        PersonalUser user = userService.findPersonalUserById(userId);
        FundBunny fundBunny = FundBunny.create(request, user);
        fundBunnyRepository.save(fundBunny);
        fundBunny.setBackerCount(BigDecimal.TEN);
        return FundBunnyResponse.from(fundBunny);
    }

    @Transactional(readOnly = true)
    public boolean checkDuplicateBunnyName(String bunnyName) {
        return fundBunnyRepository.existsByBunnyName(bunnyName) || bunnyRepository.existsByBunnyName(bunnyName);
    }

    @Transactional(readOnly = true)
    public FundBunnyDetailResponse getFundBunnyDetail(String fundBunnyId, String userId) {
        PersonalUser user = userService.findPersonalUserById(userId);
        FundBunny fundBunny = findFundBunnyById(fundBunnyId);
        return FundBunnyDetailResponse.from(fundBunny, user);
    }

    private FundBunny findFundBunnyById(String fundBunnyId) {
        return fundBunnyRepository.findById(fundBunnyId)
                .orElseThrow(() -> new FundingException(FundingError.FUND_BUNNY_NOT_FOUND));
    }

    private void validateBunnyName(String bunnyName) {
        if (bunnyName == null || bunnyName.trim().isEmpty()) {
            throw new FundingException(FundingError.BUNNY_NAME_REQUIRED);
        }
        if (bunnyName.length() < 3 || bunnyName.length() > 20) {
            throw new FundingException(FundingError.BUNNY_NAME_INVALID_LENGTH);
        }
        if (bunnyName.startsWith("-") || bunnyName.endsWith("-")) {
            throw new FundingException(FundingError.BUNNY_NAME_INVALID_HYPHEN_START_END);
        }
        if (bunnyName.contains("--")) {
            throw new FundingException(FundingError.BUNNY_NAME_INVALID_CONSECUTIVE_HYPHEN);
        }
        if (!Pattern.compile("^[a-z0-9]([a-z0-9-]*[a-z0-9])?$").matcher(bunnyName).matches()) {
            throw new FundingException(FundingError.BUNNY_NAME_INVALID_CHARACTER);
        }
    }
    
    private void validateBunnyType(BunnyType bunnyType) {
        if (bunnyType == null) {
            throw new FundingException(FundingError.BUNNY_TYPE_REQUIRED);
        }
        
        if (!Arrays.asList(BunnyType.values()).contains(bunnyType)) {
            throw new FundingException(FundingError.BUNNY_TYPE_INVALID);
        }
    }

}
