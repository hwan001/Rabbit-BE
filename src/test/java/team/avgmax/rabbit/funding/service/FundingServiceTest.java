package team.avgmax.rabbit.funding.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import team.avgmax.rabbit.bunny.entity.enums.BunnyType;
import team.avgmax.rabbit.funding.dto.request.CreateFundBunnyRequest;
import team.avgmax.rabbit.funding.dto.response.FundBunnyResponse;
import team.avgmax.rabbit.funding.entity.FundBunny;
import team.avgmax.rabbit.funding.entity.enums.FundBunnyStatus;
import team.avgmax.rabbit.funding.exception.FundingException;
import team.avgmax.rabbit.funding.exception.FundingError;
import team.avgmax.rabbit.funding.repository.FundBunnyRepository;
import team.avgmax.rabbit.bunny.repository.BunnyRepository;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.service.UserService;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FundingServiceTest {

    @InjectMocks
    private FundingService fundingService;

    @Mock
    private UserService userService;

    @Mock
    private FundBunnyRepository fundBunnyRepository;
    
    @Mock
    private BunnyRepository bunnyRepository;

    @Test
    void createFundBunny_유효한요청_펀드버니생성성공() {
        // given
        String bunnyName = "valid-bunny";
        BunnyType bunnyType = BunnyType.A;
        String userId = "test-user-id";
        
        CreateFundBunnyRequest request = new CreateFundBunnyRequest(bunnyName, bunnyType);
        PersonalUser user = PersonalUser.builder()
                .id(userId)
                .build();
        
        FundBunny savedFundBunny = FundBunny.builder()
                .id("fund-bunny-id")
                .user(user)
                .bunnyName(bunnyName)
                .type(bunnyType)
                .status(FundBunnyStatus.ONGOING)
                .backerCount(BigDecimal.ZERO)
                .build();
        
        when(fundingService.checkDuplicateBunnyName(bunnyName)).thenReturn(false);
        when(fundBunnyRepository.save(any(FundBunny.class))).thenReturn(savedFundBunny);
        
        // when
        FundBunnyResponse result = fundingService.createFundBunny(request, user.getId());
        
        // then
        assertThat(result).isNotNull();
        assertThat(result.fundBunnyId()).isEqualTo("fund-bunny-id");
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.bunnyName()).isEqualTo(bunnyName);
        assertThat(result.bunnyType()).isEqualTo(bunnyType);
    }

    // bunnyName 양식 오류 테스트 케이스들
    
    @Test
    void createFundBunny_null버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest(null, BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_REQUIRED.getMessage());
    }

    @Test
    void createFundBunny_빈문자열버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_REQUIRED.getMessage());
    }

    @Test
    void createFundBunny_짧은버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("ab", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_LENGTH.getMessage());
    }

    @Test
    void createFundBunny_긴버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("a".repeat(21), BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_LENGTH.getMessage());
    }

    @Test
    void createFundBunny_하이픈시작버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("-invalid", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_HYPHEN_START_END.getMessage());
    }

    @Test
    void createFundBunny_하이픈끝버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("invalid-", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_HYPHEN_START_END.getMessage());
    }

    @Test
    void createFundBunny_연속하이픈버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("dev--team", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_CONSECUTIVE_HYPHEN.getMessage());
    }

    @Test
    void createFundBunny_대문자포함버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("InvalidName", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_CHARACTER.getMessage());
    }

    @Test
    void createFundBunny_특수문자포함버니이름_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("invalid@name", BunnyType.A);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_INVALID_CHARACTER.getMessage());
    }

    @Test
    void createFundBunny_중복버니이름_예외발생() {
        // given
        // PersonalUser savedUser = PersonalUser.builder().id("test-user1").build();
        PersonalUser newUser = PersonalUser.builder().id("test-user2").build();
        
        // FundBunny savedFundBunny = FundBunny.builder()
        //         .id("fund-bunny-id")
        //         .user(savedUser)
        //         .bunnyName("duplicate-name")
        //         .type(BunnyType.A)
        //         .status(FundBunnyStatus.ONGOING)
        //         .backerCount(BigDecimal.ZERO)
        //         .build();
        CreateFundBunnyRequest newRequest = CreateFundBunnyRequest.builder()
                .bunnyName("duplicate-name")
                .bunnyType(BunnyType.B)
                .build();

        when(fundBunnyRepository.existsByBunnyName("duplicate-name")).thenReturn(true);
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(newRequest, newUser.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_NAME_DUPLICATE.getMessage());
        
    }

    // BunnyType 유효성 검증 테스트 케이스들

    @Test
    void createFundBunny_null버니타입_예외발생() {
        // given
        CreateFundBunnyRequest request = new CreateFundBunnyRequest("valid-name", null);
        PersonalUser user = PersonalUser.builder().id("test-user").build();
        
        // when & then
        assertThatThrownBy(() -> fundingService.createFundBunny(request, user.getId()))
                .isInstanceOf(FundingException.class)
                .hasMessage(FundingError.BUNNY_TYPE_REQUIRED.getMessage());
    }

    @Test
    void createFundBunny_B타입유효요청_생성성공() {
        // given
        String bunnyName = "balance-bunny";
        BunnyType bunnyType = BunnyType.B;
        String userId = "test-user-id";
        
        CreateFundBunnyRequest request = new CreateFundBunnyRequest(bunnyName, bunnyType);
        PersonalUser user = PersonalUser.builder()
                .id(userId)
                .build();
        
        FundBunny savedFundBunny = FundBunny.builder()
                .id("fund-bunny-id")
                .user(user)
                .bunnyName(bunnyName)
                .type(bunnyType)
                .status(FundBunnyStatus.ONGOING)
                .backerCount(BigDecimal.ZERO)
                .build();
        
        when(fundingService.checkDuplicateBunnyName(bunnyName)).thenReturn(false);
        when(fundBunnyRepository.save(any(FundBunny.class))).thenReturn(savedFundBunny);
        
        // when
        FundBunnyResponse result = fundingService.createFundBunny(request, user.getId());
        
        // then
        assertThat(result.bunnyType()).isEqualTo(BunnyType.B);
        assertThat(result.marketCap()).isEqualTo(BigDecimal.valueOf(10_000_000));
        assertThat(result.totalSupply()).isEqualTo(BigDecimal.valueOf(100_000));
        assertThat(result.price()).isEqualTo(BigDecimal.valueOf(1_000));
    }

    @Test
    void createFundBunny_C타입유효요청_생성성공() {
        // given
        String bunnyName = "cheap-bunny";
        BunnyType bunnyType = BunnyType.C;
        String userId = "test-user-id";
        
        CreateFundBunnyRequest request = new CreateFundBunnyRequest(bunnyName, bunnyType);
        PersonalUser user = PersonalUser.builder()
                .id(userId)
                .build();
        
        FundBunny savedFundBunny = FundBunny.builder()
                .id("fund-bunny-id")
                .user(user)
                .bunnyName(bunnyName)
                .type(bunnyType)
                .status(FundBunnyStatus.ONGOING)
                .backerCount(BigDecimal.ZERO)
                .build();
        
        when(fundingService.checkDuplicateBunnyName(bunnyName)).thenReturn(false);
        when(fundBunnyRepository.save(any(FundBunny.class))).thenReturn(savedFundBunny);
        
        // when
        FundBunnyResponse result = fundingService.createFundBunny(request, user.getId());
        
        // then
        assertThat(result.bunnyType()).isEqualTo(BunnyType.C);
        assertThat(result.marketCap()).isEqualTo(BigDecimal.valueOf(10_000_000));
        assertThat(result.totalSupply()).isEqualTo(BigDecimal.valueOf(1_000_000));
        assertThat(result.price()).isEqualTo(BigDecimal.valueOf(100));
    }
}