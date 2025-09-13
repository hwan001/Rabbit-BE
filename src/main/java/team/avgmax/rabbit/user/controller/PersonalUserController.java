package team.avgmax.rabbit.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.avgmax.rabbit.user.dto.request.UpdatePersonalUserRequest;
import team.avgmax.rabbit.user.dto.response.CarrotsResponse;
import team.avgmax.rabbit.user.dto.response.HoldBunniesResponse;
import team.avgmax.rabbit.user.dto.response.OrdersResponse;
import team.avgmax.rabbit.user.dto.response.PersonalUserResponse;
import team.avgmax.rabbit.user.service.PersonalUserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/personal")
public class PersonalUserController {

    private final PersonalUserService personalUserService;
    
    @GetMapping("/me/info")
    public ResponseEntity<PersonalUserResponse> getMyInfo(@AuthenticationPrincipal Jwt jwt) {
        String personalUserId = jwt.getSubject();
        log.info("내 정보 조회: {}", personalUserId);
        
        return ResponseEntity.ok(personalUserService.getUserById(personalUserId));
    }

    @PutMapping("/me/info")
    public ResponseEntity<PersonalUserResponse> updateMyInfo(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdatePersonalUserRequest request) {
        String personalUserId = jwt.getSubject();
        log.info("내 정보 수정 : {}", personalUserId);
        return ResponseEntity.ok(personalUserService.updateUserById(personalUserId, request));
    }   

    @GetMapping("/me/carrots")
    public ResponseEntity<CarrotsResponse> getMyCarrots(@AuthenticationPrincipal Jwt jwt) {
        String personalUserId = jwt.getSubject();
        log.info("내 보유 캐럿 조회: {}", personalUserId);

        return ResponseEntity.ok(personalUserService.getCarrotsById(personalUserId));
    }   

    @GetMapping("/me/hold-bunnies")
    public ResponseEntity<HoldBunniesResponse> getMyHoldBunnies(@AuthenticationPrincipal Jwt jwt) {
        String personalUserId = jwt.getSubject();
        log.info("내 보유 버니 조회: {}", personalUserId);

        return ResponseEntity.ok(personalUserService.getBunniesById(personalUserId));
    }

    @GetMapping("/me/orders")
    public ResponseEntity<OrdersResponse> getMyOrders(@AuthenticationPrincipal Jwt jwt) {
        String personalUserId = jwt.getSubject();
        log.info("내 주문 조회: {}", personalUserId);  

        return ResponseEntity.ok(personalUserService.getOrdersById(personalUserId));
    }

}