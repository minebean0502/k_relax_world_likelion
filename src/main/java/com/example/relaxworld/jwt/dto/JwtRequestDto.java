package com.example.relaxworld.jwt.dto;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String userId;
    private String password;
}