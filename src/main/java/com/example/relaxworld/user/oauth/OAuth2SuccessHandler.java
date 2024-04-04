package com.example.relaxworld.user.oauth;

import com.example.relaxworld.jwt.JwtTokenUtils;
import com.example.relaxworld.user.dto.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor// OAuth2UserServiceImpl이 성공적으로 OAuth2 과정을 마무리 했을 때,
// 넘겨 받을 사용자 정보를 바탕으로 JWT 생성,
// 클라이언트한테 JWT를 전달
public class OAuth2SuccessHandler
        // 인증에 성공했을 때 특정 URL로 리다이렉트 하고 싶은 경우 활용 가능한 SuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {
    // JWT 발급을 위해 JwtTokenUtils
    private final JwtTokenUtils tokenUtils;
    // 사용자 정보 등록을 위해 UserDetailsManager
    private final UserDetailsManager userDetailsManager;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String provider = oAuth2User.getAttribute("provider");
        String nickname = oAuth2User.getAttribute("nickname");
        String providerId = oAuth2User.getAttribute("id").toString();
        String username = String.format("{%s} %s", provider, nickname);

        if (!userDetailsManager.userExists(username)) {
            userDetailsManager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .userId(providerId)
                    .password(providerId)
                    .userRole("ROLE_REGISTER")
                    .build());
        }

        UserDetails details = userDetailsManager.loadUserByUsername(username);
        String jwt = tokenUtils.generateToken(details);

        // AccessToken 추출
        String accessToken = "";
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
            if (client != null) {
                accessToken = client.getAccessToken().getTokenValue();
            }
        }

        // 리다이렉션 URL에 accessToken 포함
        String targetUrl = "/loginSuccess?token=" + jwt + "&kakaoAccessToken=" + accessToken;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}