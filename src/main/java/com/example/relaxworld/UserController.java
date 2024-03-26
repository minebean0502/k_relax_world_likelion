package com.example.relaxworld;

import com.example.relaxworld.dto.CustomUserDetails;
import com.example.relaxworld.dto.JwtRequestDto;
import com.example.relaxworld.dto.JwtResponseDto;
import com.example.relaxworld.entity.ModifyPasswordRequest;
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

    // id찾기 페이지 /v1/user/idpw/id/find
    @GetMapping("idpw/id/find")
    public String idFind(
            @RequestParam("phone_number")
            String phoneNumber
    ) {
        return manager.idFind(phoneNumber);
    }

    // pw찾기 페이지 /v1/user/idpw/pw/find
    @GetMapping("idpw/pw/find")
    public String pwFind(
            @RequestParam("userId")
            String userId,
            @RequestParam("phone_number")
            String phoneNumber
    ) {
        return manager.pwFind(userId, phoneNumber);
    }

    // (임시)비밀번호 수정 /v1/user/idpw/modify
    @PostMapping("idpw/modify")
    public String idpwModify(
            @RequestBody
            ModifyPasswordRequest request
    ) {
        return manager.updatePassword(request);
    }
}
