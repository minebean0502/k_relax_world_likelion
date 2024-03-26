package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.entity.PayUser;
import com.example.relaxworld.pay.repository.PayUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PayUserServiceImpl implements PayUserService {
    private final PayUserRepository payUserRepository;

    // 회원 자동 생성
    @Override
    public PayUser autoRegister() {
        PayUser payUser = PayUser.builder()
                .username("탁민렬")
               // .username(UUID.randomUUID().toString())
                .phoneNumber("010-1234-5678")
                .userId("user")
                .password("password")
                .build();

        return payUserRepository.save(payUser);
    }
}
