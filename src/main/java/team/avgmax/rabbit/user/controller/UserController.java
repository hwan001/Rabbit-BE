package team.avgmax.rabbit.user.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import team.avgmax.rabbit.user.service.PersonalUserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final PersonalUserService personalUserService;
}
