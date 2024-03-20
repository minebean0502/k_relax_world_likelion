package com.example.relaxworld.waste.controller;

import com.example.relaxworld.waste.service.FormService;
import com.example.relaxworld.waste.service.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
// 메인서비스 1 : 대형 폐기물 스티커 서비스
@RestController("waste")
@RequestMapping
@RequiredArgsConstructor
public class ApplyController {
    private final FormService formService;
    private final ApplyService applyService;

    // [대형 폐기물 스티커 서비스] -> [폐기물 종류 선택 화면]
    @GetMapping("/waste-items")
    public void selectWastes() {

        // 폐기물의 종류와, 수량을 선택합니다

        // 그 뒤에 해당 폐기물의 [배출 날짜 및 시간, 장소 선택 화면]으로 이동
    }

    // [폐기물 종류 선택 화면] -> [배출 날짜 및 시간, 장소 선택 화면]
    @GetMapping("/waste-items/date-selection")
    public void selectDate(
            Model model
            // Model 구조를 쓸지 dto 구조를 쓸지는 조금 고민
            // Long id, WasteDto dto
    ) {
        // "해당" 폐기물의 날짜까지 선택이 끝나면

        // 완료 -> 신청서 확인으로 갈지

        // 계속하기 -> 다시 폐기물 종류, 수량 선택 페이지로 돌아가 추가할지

        // 취소 -> 서비스 메인화면으로 갈지 선택합니다.
    }

    // [시간 선택] -> [신청 페이지 확인 페이지]
    @GetMapping("/waste-items/date-selection/application-form")
    public void checkApplyForm() {

        // 현재까지 신청 목록을 보여준 뒤에

        // 결제하기 -> 결제 페이지로 이동

        // 취소 -> 서비스 메인화면으로 이동
    }

    // [결제 수단 선택 // 신용카드 or 간편 결제]

}
