package team.avgmax.rabbit.bunny.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import team.avgmax.rabbit.bunny.service.BunnyService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BunnyController {

    private final BunnyService bunnyService;
}
