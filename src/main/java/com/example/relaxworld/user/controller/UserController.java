package com.example.relaxworld.user.controller;

import com.example.relaxworld.AuthenticationFacade;
import com.example.relaxworld.jwt.JwtTokenUtils;
import com.example.relaxworld.user.service.JpaUserDetailsManager;
import com.example.relaxworld.user.dto.CustomUserDetails;
import com.example.relaxworld.jwt.dto.JwtRequestDto;
import com.example.relaxworld.jwt.dto.JwtResponseDto;
import com.example.relaxworld.user.entity.ModifyPasswordRequest;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final JpaUserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationFacade authFacade;

    // 로그인 v1/user/login
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(
            @RequestBody
            JwtRequestDto dto
    ) {
        JwtResponseDto jwtResponseDto = manager.login(dto);
        log.info(jwtResponseDto.getToken());
        return ResponseEntity.ok(jwtResponseDto);
    }

    // 회원가입 v1/user/signup
    @PostMapping("/signup")
    public String signUp(
            @RequestParam("username")
            String username,
            @RequestParam("userId")
            String userId,
            @RequestParam("password")
            String password,
            @RequestParam("password-check")
            String passwordCheck,
            @RequestParam("phoneNumber")
            String phoneNumber
    ) {
        if (password.equals(passwordCheck)){
            manager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .userId(userId)
                    .password(passwordEncoder.encode(password))
                    .phoneNumber(phoneNumber)
                    .build());
            // 회원가입 성공 후 로그인 페이지로 이동
            return "redirect:/home";
        }
        else{
            // 비밀번호 불일치
            return "redirect:/register";
        }
    }

    // id찾기 페이지 /v1/user/idpw/id/find
    @ResponseBody
    @GetMapping("/idpw/id/find")
    public String idFind(
            @RequestParam("phone_number")
            String phoneNumber
    ) {
        return manager.idFind(phoneNumber);
    }

    // pw찾기 페이지 /v1/user/idpw/pw/find
    @ResponseBody
    @GetMapping("/idpw/pw/find")
    public String pwFind(
            @RequestParam("userId")
            String userId,
            @RequestParam("phone_number")
            String phoneNumber
    ) {
        return manager.pwFind(userId, phoneNumber);
    }

    // (임시)비밀번호 수정 /v1/user/idpw/modify
    @ResponseBody
    @PostMapping("/idpw/modify")
    public String idpwModify(
            @RequestBody
            ModifyPasswordRequest request
    ) {
        return manager.updatePassword(request);
    }

    @GetMapping("/validate")
    public String validateToken(
            @RequestParam("token") String token,
            Model model
    ) {
        if (!jwtTokenUtils.validate(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            // JWT is valid, proceed to success page
            Claims claims = jwtTokenUtils.parseClaims(token);
            // 제대로 jwt 토큰을 가져왔는지 출력 확인용
            model.addAttribute("jwt", token);
            model.addAttribute("jwt_username", claims.getSubject());
            // todo Thymeleaf를 이용해서 jwt, jwt의 내용을 가져올 수 있음 login-success.html 참고
            return "user/login-success";
            // 로그인 성공시 이동할 본 서비스
//            return "redirect:/v1/user/service";
        }
    }
}
