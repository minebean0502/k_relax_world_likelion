package com.example.relaxworld.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class UserFormController {
    // 홈 화면 자동 리다이렉트
    @GetMapping
    public String toHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/user/home";
    }

    @GetMapping("/register")
    public String toRegister() {
        return "/user/register";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "/user/login2";
    }

    @GetMapping("/idpw/id/find")
    public String toFindId(){
        return "/user/idfind";
    }
    @GetMapping("/idpw/pw/find")
    public String toFindPw() {
        return "/user/pwfind";
    }

    @GetMapping("/modify")
    public String toModify() {
        return "/user/modify";
    }

    @GetMapping("/services")
    public String toServices() {
        return "/common/services";
    }

    @GetMapping("/loginSuccess")
    public String kakaoLoginSuccess(
            @RequestParam
            String token,
            Model model
    ) {
        model.addAttribute("token", token);
        return "/user/login-success";
    }


    // 테스트용
    @GetMapping("/login/test")
    public String toJwtTokenTest() {
        return "/user/test";
    }
}
