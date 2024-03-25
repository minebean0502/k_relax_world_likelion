package com.example.relaxworld.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
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
}
