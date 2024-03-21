package com.example.relaxworld.waste.servicie;

import com.example.relaxworld.repo.UserRepository;
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

    // 폼 생성
    // 폐기물 종류와 수량 선택
    public void createForm() {

    }

    // 기존 폼 리스트들 확인 확인
    public void checklists() {

    }
}