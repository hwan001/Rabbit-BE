package team.avgmax.rabbit.funding.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.funding.dto.response.FundBunnyResponse;
import team.avgmax.rabbit.funding.entity.FundBunny;
import team.avgmax.rabbit.funding.exception.FundingError;
import team.avgmax.rabbit.funding.exception.FundingException;
import team.avgmax.rabbit.bunny.repository.BunnyRepository;
import team.avgmax.rabbit.funding.repository.FundBunnyRepository;
import team.avgmax.rabbit.user.service.UserService;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.bunny.entity.enums.BunnyType;

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
        checkDuplicateBunnyName(request.bunnyName());
        
        PersonalUser user = userService.findPersonalUserById(userId);
        FundBunny fundBunny = FundBunny.create(request, user);
        return FundBunnyResponse.from(fundBunnyRepository.save(fundBunny));
    }

    @Transactional(readOnly = true)
    public boolean checkDuplicateBunnyName(String bunnyName) {
        boolean isDuplicate = fundBunnyRepository.existsByBunnyName(bunnyName) || bunnyRepository.existsByBunnyName(bunnyName);
        if (isDuplicate) {
            throw new FundingException(FundingError.BUNNY_NAME_DUPLICATE);
        }
        return isDuplicate;
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
