package com.example.relaxworld.user.entity;

import lombok.Data;
import lombok.Setter;

@Data
public class ModifyPasswordRequest {
    private String userId;
    private String phoneNumber;
    @Setter
    private String newPassword;
}