package com.example.relaxworld.waste.service;

import com.example.relaxworld.user.repository.UserRepository;
import com.example.relaxworld.waste.repository.FormRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private final UserRepository userRepository;
    private final FormRepository formRepository;


    // 폐기물 종류와 수량 선택
    public void createForm() {

    }
}
