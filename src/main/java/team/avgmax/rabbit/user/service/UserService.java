package team.avgmax.rabbit.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.user.entity.PersonalUser;
import team.avgmax.rabbit.user.exception.UserError;
import team.avgmax.rabbit.user.exception.UserException;
import team.avgmax.rabbit.user.repository.PersonalUserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PersonalUserRepository personalUserRepository;

    public PersonalUser findPersonalUserById(String userId) {
        return personalUserRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }
}
