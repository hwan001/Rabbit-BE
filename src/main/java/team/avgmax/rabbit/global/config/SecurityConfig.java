package team.avgmax.rabbit.global.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    @Order(1)
    SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
      OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
      return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain appSecurity(HttpSecurity http) throws Exception  {
        http
                .securityMatcher("/login/**", "/oauth2/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                //.oauth2Login(oauth2 -> oauth2.successHandler(successHandler));
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(3)
    SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
      http
              .securityMatcher("/auth/**") // 보호할 API 경로
              .authorizeHttpRequests(auth -> auth
                      .anyRequest().authenticated()
              )
              .oauth2ResourceServer(oauth2 -> oauth2.jwt());
      return http.build();
    }

    @Bean
    RegisteredClientRepository registeredClientRepository() {
      RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
              .clientId("demo-client")
              .clientSecret("{noop}demo-secret")
              .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
              .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
              .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
              .redirectUri("http://127.0.0.1:8080/login/oauth2/code/demo-client")
              .scope("read")
              .scope("write")
              .build();

      return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() {
      RSAKey rsaKey = Jwks.generateRsa(); // 아래 Jwks 유틸 참고
      JWKSet jwkSet = new JWKSet(rsaKey);
      return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
      return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    AuthorizationServerSettings authorizationServerSettings() {
      return AuthorizationServerSettings.builder()
              .issuer("http://localhost:8080") // JWT iss 값
              .build();
    }
}