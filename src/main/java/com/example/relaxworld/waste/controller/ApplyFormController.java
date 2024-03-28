package com.example.relaxworld.waste.controller;

import com.example.relaxworld.waste.dto.FinalizeApplicationDto;
import com.example.relaxworld.waste.dto.FormDto;
import com.example.relaxworld.waste.dto.WasteApplicationDto;
import com.example.relaxworld.waste.dto.WasteItemDto;
import com.example.relaxworld.waste.service.ApplyFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("waste")
@RequiredArgsConstructor
public class ApplyFormController {
    private final ApplyFormService applyFormService;

    // 서비스를 선택한 시점에서 임시로 폼을 생성
    @PostMapping("/apply")
    public ResponseEntity<FormDto> createTempForm(
            @RequestParam
            String username
    ) {
        FormDto formDto = applyFormService.createTempForm(username);
        log.info(formDto.getId() + "번 form이 임시로 생성되었습니다.");
        return ResponseEntity.ok(formDto);
    }

    // 유저가 버릴 쓰레기를 선택할 로직
    // 다른 쓰레기를 여러번 반복해서 선택 할 수 있음 (신청 중)
    @PostMapping("/apply/add-waste-item")
    public ResponseEntity<WasteApplicationDto> addWasteItem(
            @RequestBody
            WasteItemDto wasteItemDto
    ) {
        WasteApplicationDto wasteApplicationDto = applyFormService.addWasteItem(wasteItemDto);
        return ResponseEntity.ok(wasteApplicationDto);
    }


    // 최종적으로 신청 완료 하기 위함
    @PostMapping("/apply/finalize")
    public ResponseEntity<FormDto> finalizeApplication(
            @RequestBody
            FinalizeApplicationDto finalizeDto
    ) {
        FormDto formDto = applyFormService.finalizeApplication(finalizeDto);
        return ResponseEntity.ok(formDto);
    }

    // 세대 신청현황 확인 (기존 Form 리스트들 확인)
    @GetMapping("/applications")
    public List<FormDto> readAllForm() {
        return applyFormService.readAllForms();
    }


    // Form 리스트 하나 확인
    @GetMapping("/applications/{applicationId}")
    public FormDto selectApplication(
            @PathVariable("applicationId")
            Long applicationId
    ) {
        return applyFormService.readOneForm(applicationId);
    }








    /*

    // [대형 폐기물 스티커 서비스] -> [폐기물 종류 선택 화면]
    @PostMapping("/waste-items")
    public FormDto selectWastes(
            @RequestBody
            SelectWasteFormDto dto
    ) {
        return applyFormService.selectWaste(dto);

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

     */

    // [시간 선택] -> [신청 페이지 확인 페이지]
    @GetMapping("/waste-items/date-selection/application-form")
    public void checkApplyForm() {

        // 현재까지 신청 목록을 보여준 뒤에

        // 결제하기 -> 결제 페이지로 이동

        // 취소 -> 서비스 메인화면으로 이동
    }

    // [결제 수단 선택 // 신용카드 or 간편 결제]







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
