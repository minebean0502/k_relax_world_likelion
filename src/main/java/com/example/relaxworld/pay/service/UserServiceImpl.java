package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.entity.User;
import com.example.relaxworld.pay.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .phoneNumber("010-1234-5678")
                .userId("user")
                .password("password")
                .build();
        return userRepository.save(user);
    }
}
