package team.avgmax.rabbit.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.avgmax.rabbit.user.repository.PersonalUserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PersonalUserRepository personalUserRepository;
}
