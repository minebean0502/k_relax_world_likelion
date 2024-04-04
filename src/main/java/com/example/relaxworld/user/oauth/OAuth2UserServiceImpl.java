package com.example.relaxworld.user.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OAuth2UserServiceImpl // 기본적인 OAuth2 인증 과정을 진행해주는 클래스
        extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 어떤 서비스 제공자를 사용했는지
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();
        // TODO 서비스 제공자에 따라 데이터 처리를 달리 하고 싶을 때

        // OAuth2 제공자로 부터 받은 데이터를 원하는 방식으로 다시 정리하기 위한 Map
        Map<String, Object> attributes = new HashMap<>();
        String nameAttribute = "";

        // Kakao 아이디로 로그인
        if (registrationId.equals("kakao")) {
            log.info(oAuth2User.getAttributes().toString());
            // Kakao에서 받아온 정보다
            attributes.put("provider","kakao");
            attributes.put("id",oAuth2User.getAttribute("id"));
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            attributes.put("email", kakaoAccount.get("email"));
            Map<String, Object> kakaoProfile
                    = (Map<String, Object>) kakaoAccount.get("profile");
            attributes.put("nickname",kakaoProfile.get("nickname"));
            attributes.put("profile_image_url", kakaoProfile.get("profile_image_url"));
            nameAttribute = "nickname";
        }

        log.info(attributes.toString());
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_REGISTER")),
                attributes,
                nameAttribute
        );
    }
}

