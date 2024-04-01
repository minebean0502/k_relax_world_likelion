package com.example.relaxworld.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "/user/login";
    }

    @GetMapping("/services")
    public String toServices() {
        return "/common/services";
    }

    // 테스트용
    @GetMapping("/login/test")
    public String toJwtTokenTest() {
        return "/user/test";
    }
}
