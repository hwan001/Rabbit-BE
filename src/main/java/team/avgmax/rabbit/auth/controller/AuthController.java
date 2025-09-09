package team.avgmax.rabbit.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(401).body(Map.of("error", "No refresh token"));
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(c -> "REFRESH_TOKEN".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            return ResponseEntity.status(401).body(Map.of("error", "No refresh token"));
        }

        try {
            Jwt jwt = jwtDecoder.decode(refreshToken);

            if (!"refresh".equals(jwt.getClaim("type"))) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid token type"));
            }

            Instant now = Instant.now();
            long accessExpiry = 60L * 15; // Access Token 15분

            JwtClaimsSet accessClaims = JwtClaimsSet.builder()
                    .issuer("rabbit-app")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(accessExpiry))
                    .subject(jwt.getSubject())
                    // refresh 토큰에 roles 안 넣었다면, DB 조회해서 roles 다시 가져오는 게 안전
                    .claim("roles", jwt.getClaim("roles"))
                    .build();

            String newAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessClaims)).getTokenValue();

            ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", newAccessToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("Strict")
                    .path("/")
                    .maxAge(accessExpiry)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

            return ResponseEntity.ok(Map.of("message", "new access token issued"));

        } catch (JwtException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid refresh token"));
        }
    }

    @GetMapping("/me")
    public Map<String, Object> user(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "sub", jwt.getSubject(),
                "roles", jwt.getClaim("roles")
        );
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // ACCESS_TOKEN 만료
        ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)  // 즉시 만료
                .build();

        // REFRESH_TOKEN 만료
        ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/auth/refresh")
                .maxAge(0)  // 즉시 만료
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        return ResponseEntity.noContent().build(); // 204 No Content
    }

}