package com.example.relaxworld.waste.controller;

import com.example.relaxworld.waste.dto.FormDto;
import com.example.relaxworld.waste.service.FormService;
import com.example.relaxworld.waste.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("waste1")
@RequestMapping
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;
    private final ApplyService applyService;

    // 세대 신청현황 확인 (기존 Form 리스트들 확인)
    @GetMapping("/applications")
    // public FormDto formlists() { // 나중에 이걸로 바꾸기
    public void checkApplications() {

        // 그 사람 ID로 신청한 list들 반환
        // return formService.checklists();
    }


    // Form 리스트 하나 확인
    @GetMapping("/applications/{applicationId}")
    public void selectApplication(
            @PathVariable("applicationId")
            Long id,
            FormDto dto
    ) {

    }

    // 결제 취소 신청 페이지 [당분간은 안건드림
    @GetMapping("/applications/{applicationId}/refund")
    public void refundApplication(
            @PathVariable("applicationId")
            Long id,
            FormDto dto
    ) {

    }

    // 신청 페이지 확인 화면에서 수정 하러 갈 때 쓸 것, 일단 더 고민

}
