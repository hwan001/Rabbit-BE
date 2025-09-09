package team.avgmax.rabbit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.entity.enums.Role;
import team.avgmax.rabbit.user.entity.UserProvider;
import team.avgmax.rabbit.user.entity.enums.ProviderType;
import team.avgmax.rabbit.user.repository.PersonalUserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonalUserService {

    private final PersonalUserRepository personalUserRepository;

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
            user.addProvider(userProvider); // 연관관계 편의 메서드
        }

        return user;
    }
}