package com.example.relaxworld.pay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class PayUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String username;
    @Setter
    private String userId;
    @Setter
    private String password;
    @Setter
    private String phoneNumber;
    @Setter
    private String UserRole = "ROLE_REGISTER";
    @Builder
    public PayUser(String username, String userId, String password, String phoneNumber) {
        this.username = username;
        this.userId=userId;
        //this.password = password;
        this.phoneNumber= phoneNumber;
    }
}
