package team.avgmax.rabbit.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;

import team.avgmax.rabbit.auth.config.CorsConfig;
import team.avgmax.rabbit.auth.oauth2.CookieBearerTokenResolver;
import team.avgmax.rabbit.auth.oauth2.CustomOAuth2UserService;
import team.avgmax.rabbit.auth.oauth2.CustomSuccessHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final CookieBearerTokenResolver cookieBearerTokenResolver;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorization")) // 기본값
                .redirectionEndpoint(redirection -> redirection.baseUri("/login/oauth2/code/*"))
                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .bearerTokenResolver(cookieBearerTokenResolver)
                .jwt(Customizer.withDefaults())
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login/**", "/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/**").permitAll() // 공개 API 있다면
                .requestMatchers("/admin/**").hasRole("ADMIN") // "ROLE_ADMIN"이랑 매칭됨
                .requestMatchers("/user/**", "/auth/**").hasRole("USER")
                .anyRequest().hasRole("USER")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
}