package com.example.relaxworld.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 유저의 PK
    @Setter
    private String username;            // 유저의 이름
    @Setter
    private String userId;              // 유저의 ID
    @Setter
    private String password;            // 유저의 비밀번호
    @Setter
    private String phoneNumber;
    @Setter
    private String userRole;

    /*
    @Setter
    private String UserRole = "ROLE_REGISTER";
    @Builder
    public User(String username, String userId,String password, String phoneNumber) {
        this.username = username;
        this.userId=userId;
        //this.password = password;
        this.phoneNumber= phoneNumber;
    }
     */
}
