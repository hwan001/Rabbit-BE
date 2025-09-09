package team.avgmax.rabbit.auth.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtEncoder jwtEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        Instant now = Instant.now();

        // Access Token (15분)
        long accessExpiry = 60L * 15;

        JwtClaimsSet accessClaims = JwtClaimsSet.builder()
                .issuer("rabbit-app")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(accessExpiry))
                .subject(authentication.getName())
                .claim("roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, accessClaims)).getTokenValue();

        // Refresh Token (1일)
        long refreshExpiry = 60L * 60 * 24;

        JwtClaimsSet refreshClaims = JwtClaimsSet.builder()
                .issuer("rabbit-app")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(refreshExpiry))
                .subject(authentication.getName())
                .claim("type", "refresh")
                .build();

        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, refreshClaims)).getTokenValue();

        // 쿠키 생성
        ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", accessToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(accessExpiry)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/auth/refresh")
                .maxAge(refreshExpiry)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"login success\"}");
    }
}