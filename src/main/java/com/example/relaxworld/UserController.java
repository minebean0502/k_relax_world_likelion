package com.example.relaxworld;

import com.example.relaxworld.dto.CustomUserDetails;
import com.example.relaxworld.dto.JwtRequestDto;
import com.example.relaxworld.dto.JwtResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("v1/user")
@RequiredArgsConstructor
public class UserController {
    private final JpaUserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;

    // 로그인 v1/user/login
    @PostMapping("login")
    public JwtResponseDto login(
            @RequestBody
            JwtRequestDto dto
    ) {
        return manager.login(dto);
    }

    // 회원가입 v1/user/signup
    @PostMapping("signup")
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
        return "회원가입 성공";
        }
        else{
            // 비밀번호 불일치
            return "비밀번호 불일치";
        }
    }
}
