package com.example.relaxworld.config;

import com.example.relaxworld.jwt.JwtTokenFilter;
import com.example.relaxworld.jwt.JwtTokenUtils;
import com.example.relaxworld.user.oauth.OAuth2SuccessHandler;
import com.example.relaxworld.user.oauth.OAuth2UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager manager;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                // csrf  보안 헤제
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        /*
                        // 권한이 없는 사람만 접근 가능
                        // 프론트용
                        .requestMatchers(
                                // user 관련
                                "/home",
                                "/reigster",
                                "/login",
                                "/idpw/**"
                        )
                        .anonymous()

                        // 권한이 없는 사람만 접근 가능
                        // 백엔드용
                        .requestMatchers(
                                // user 관련
                                "/v1/user/**"
                        )
                        .anonymous()

                        // 권한이 있어야만 접근 가능
                        // 프론트용
                        .requestMatchers(
                                // common
                                // "/services",

                                // waste 관련
                                "/waste/**"
                        )
                        .authenticated()

                        // 권한이 있어야만 접근 가능
                        // 백엔드용
                        .requestMatchers(
                                "/v1/user/**"
                        )
                        .authenticated()

                        // 모든 사람에 대한 접근 가능
                        .requestMatchers(
                                // static 폴더에 대한 경로는 모두 ON
                                "/js/**",
                                "/css/**",
                                "/images/**"
                        )
                        // static에 대한 모든 경로 허용
                        .permitAll()

                        // 테스트용
                        .requestMatchers(
                                "/services"
                        )
                        .permitAll()
                         */
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/v1/user/login")
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                )

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(
                        new JwtTokenFilter(
                                jwtTokenUtils,
                                manager
                        ),
                        AuthorizationFilter.class
                );
        return http.build();
    }
}