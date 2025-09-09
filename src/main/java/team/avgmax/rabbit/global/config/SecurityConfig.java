//package team.avgmax.rabbit.global.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        //csrf disable
//        http
//                .csrf(AbstractHttpConfigurer::disable);
//
//        //From 로그인 방식 disable
//        http
//                .formLogin(AbstractHttpConfigurer::disable);
//
//        //HTTP Basic 인증 방식 disable
//        http
//                .httpBasic(AbstractHttpConfigurer::disable);
//
//        //경로별 인가 작업
//        http
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers(HttpMethod.POST, "/fund-bunnies").permitAll()
//                        .anyRequest().authenticated());
//        //세션 설정 : STATELESS
//        http
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//}
