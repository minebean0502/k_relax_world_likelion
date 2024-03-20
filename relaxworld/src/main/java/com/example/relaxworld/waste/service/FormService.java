package com.example.relaxworld.waste.service;

import com.example.relaxworld.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormService {
    private final UserRepository userRepository;

    // 폼 생성
    public void createForm() {

    }

    // 기존 폼 리스트들 확인 확인
    public void checklists() {

    }
}
