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
    private String userRole;
    @Setter
    private String image;

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
