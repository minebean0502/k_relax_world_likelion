package com.example.relaxworld.service;

import com.example.relaxworld.entity.User;
import com.example.relaxworld.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    // 회원 자동 생성
    @Override
    public User autoRegister() {
        User user = User.builder()
                .username("탁민렬")
               // .username(UUID.randomUUID().toString())
                .phoneNumber("010-1234-5678")
                .userId("user")
                .password("password")
                .build();

        return userRepository.save(user);
    }
}
