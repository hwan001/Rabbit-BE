package team.avgmax.rabbit.user.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.avgmax.rabbit.user.dto.request.PersonalUserRequest;
import team.avgmax.rabbit.user.dto.request.EducationRequest;

import team.avgmax.rabbit.user.dto.response.CarrotsResponse;
import team.avgmax.rabbit.user.dto.response.HoldBunniesResponse;
import team.avgmax.rabbit.user.dto.response.OrdersResponse;
import team.avgmax.rabbit.user.dto.response.PersonalUserResponse;
import team.avgmax.rabbit.user.entity.Career;
import team.avgmax.rabbit.user.entity.Education;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.entity.enums.Role;
import team.avgmax.rabbit.user.exception.UserError;
import team.avgmax.rabbit.user.exception.UserException;
import team.avgmax.rabbit.user.entity.UserProvider;
import team.avgmax.rabbit.user.entity.enums.ProviderType;
import team.avgmax.rabbit.user.repository.PersonalUserRepository;
import team.avgmax.rabbit.user.repository.CareerRepository;
import team.avgmax.rabbit.user.repository.EducationRepository;
import team.avgmax.rabbit.user.repository.custom.HoldBunnyRepositoryCustomImpl;
import team.avgmax.rabbit.user.repository.custom.OrderRepositoryCustomImpl;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonalUserService {

    private final PersonalUserRepository personalUserRepository;
    private final OrderRepositoryCustomImpl orderRepositoryCustom;
    private final HoldBunnyRepositoryCustomImpl holdBunnyRepositoryCustom;
    private final CareerRepository careerRepository;
    private final EducationRepository educationRepository;

    public PersonalUser findOrCreateUser(String email, String name, String registrationId, String providerId) {
        PersonalUser user = personalUserRepository.findByEmail(email)
                .orElseGet(() -> personalUserRepository.save(
                        PersonalUser.builder()
                                .email(email)
                                .name(name)
                                .role(Role.ROLE_USER)
                                .build()
                ));

        ProviderType providerType = ProviderType.from(registrationId);

        boolean exists = user.getProviders().stream()
                .anyMatch(p -> p.getProviderType() == providerType);

        if (!exists) {
            UserProvider userProvider = UserProvider.of(providerType, providerId);
            user.addProvider(userProvider);
        }

        return user;
    }

    public PersonalUserResponse getUserById(String personalUserId) {
        PersonalUser personalUser = personalUserRepository.findById(personalUserId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        return PersonalUserResponse.from(personalUser);
    }

    public CarrotsResponse getCarrotsById(String personalUserId) {
        PersonalUser personalUser = personalUserRepository.findById(personalUserId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
        
        return CarrotsResponse.from(personalUser);
    }

    public HoldBunniesResponse getBunniesById(String personalUserId) {
        return holdBunnyRepositoryCustom.findHoldbunniesByUserId(personalUserId);
    }

    public OrdersResponse getOrdersById(String personalUserId) {
        return orderRepositoryCustom.findOrdersByUserId(personalUserId);
    }

    public PersonalUser findPersonalUserById(String userId) {
        return personalUserRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

    @Transactional
    public void updateMyInfo(String personalUserId, PersonalUserRequest personalUserRequest) {
        PersonalUser personalUser = personalUserRepository.findById(personalUserId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));

        personalUser.updateBasicInfo(
                personalUserRequest.name(), 
                personalUserRequest.birthdate(), 
                personalUserRequest.image(), 
                personalUserRequest.resume(), 
                personalUserRequest.position()
        );

        // careerRepository.deleteByPersonalUserId(personalUserId);
        // for (CareerRequest c : personalUserRequest.career()) {
        //     personalUser.addCareer(Career.create(c));
        // }

        // educationRepository.deleteByPersonalUserId(personalUserId);
        // for (EducationRequest e : personalUserRequest.education()) {
        //     personalUser.addEducation(Education.create(e));
        // }

        // certificationRepository.deleteByUser(user);
        // for (CertificationRequest cert : request.certification()) {
        //     user.addCertification(new Certification(...));
        // }

        // skillRepository.deleteByUser(user);
        // for (String s : request.skill()) {
        //     user.addSkill(new Skill(s));
        // }
    }
}