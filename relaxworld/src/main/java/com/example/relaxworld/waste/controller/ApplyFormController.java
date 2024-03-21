package com.example.relaxworld.waste.controller;

import com.example.relaxworld.waste.dto.FormDto;
import com.example.relaxworld.waste.servicie.ApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
// 메인서비스 1 : 대형 폐기물 스티커 서비스
@RestController("waste")
@RequestMapping
@RequiredArgsConstructor
public class ApplyFormController {
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

    // ------------------------------------------------//

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
