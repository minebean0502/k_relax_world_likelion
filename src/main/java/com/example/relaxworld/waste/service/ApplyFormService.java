package com.example.relaxworld.waste.service;

import com.example.relaxworld.entity.User;
import com.example.relaxworld.repo.UserRepository;
import com.example.relaxworld.waste.dto.*;
import com.example.relaxworld.waste.entity.FormEntity;
import com.example.relaxworld.waste.entity.WasteApplicationEntity;
import com.example.relaxworld.waste.entity.WasteEntity;
import com.example.relaxworld.waste.repository.FormRepository;
import com.example.relaxworld.waste.repository.WasteApplicationRepository;
import com.example.relaxworld.waste.repository.WasteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyFormService {
    private final UserRepository userRepository;
    private final FormRepository formRepository;
    private final WasteRepository wasteRepository;
    private final WasteApplicationRepository wasteApplicationRepository;

    public Long createTempForm(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // 임시로 form을 만든다
        FormEntity formEntity = FormEntity.builder()
                .user(user)
                .build();
        // 폼을 저장시키고 생성된 formEntity의 ID 반환
        formEntity = formRepository.save(formEntity);
        return formEntity.getId();
    }

    public void addWasteItem(WasteItemDto wasteItemDto) {
        // 쓰레기에 대한 정보 찾기
        WasteEntity wasteEntity = wasteRepository.findById(wasteItemDto.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Waste not found with id: " + wasteItemDto.getId()));

        // 임시로 저장했던 Form에 대한 정보 찾기
        FormEntity formEntity = formRepository.findById(wasteItemDto.getFormId())
                .orElseThrow(() -> new RuntimeException(
                        "Form not found with id: " + wasteItemDto.getFormId()));

        // WasteApplicationEntity에 계속해서 정보 저장하기
        WasteApplicationEntity wasteApplication
                = WasteApplicationEntity.builder()
                .waste(wasteEntity)
                .form(formEntity)
                .date(wasteItemDto.getDate())
                .quantity(wasteItemDto.getQuantity())
                .build();

        wasteApplicationRepository.save(wasteApplication);
    }

    // 최종 신청서 신청 로직
    public FormDto finalizeApplication(FinalizeApplicationDto finalizeDto) {
        // 최종으로 신청할 username을 찾는다
        User user = userRepository.findByUsername(finalizeDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        FormEntity formEntity = formRepository.findById(finalizeDto.getFormId())
                .orElseThrow(() -> new RuntimeException("Form not found with id: " + finalizeDto.getFormId()));

        // FormEntity 최종 생성 및 저장 로직
//        FormEntity formEntity = FormEntity.builder()
//                .user(user)
//                .status("Pending")
//                .totalPrice(0)      // 최초 생성시 0원으로 초기화
//                .build();

        // 이후 totalPrice를 계산하는 로직
        Integer totalPrice = 0;
        for (WasteItemDto item : finalizeDto.getWasteItems()) {
            // WasteEntity의 정보 가져오기
            WasteEntity wasteEntity = wasteRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Waste not found"));

            totalPrice += wasteEntity.getPrice() * item.getQuantity();
        }

        // 모든 순회가 끝나면 totalPrice에 대해 적립이 완전히 끝나있을 것
        // 마지막으로 totalPrice에 대한 정보를 저장하고, 리턴해줄 것
        formEntity.setTotalPrice(totalPrice);
        formRepository.save(formEntity);

        return FormDto.fromEntity(formEntity);
    }














//    // 사용자가 사용자와, 쓰레기와, 이름, 갯수, 날짜에 대한 정보를 송신 (1회)
//    public FormDto selectWaste(SelectWasteFormDto dto) {
//        // 사용자 찾기
//        User user = userRepository.findByUsername(dto.getUsername())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 폐기물 찾기
//        WasteEntity wasteEntity = wasteRepository.findById(dto.getId())
//                .orElseThrow(() -> new RuntimeException("Waste not found"));
//
//        // Form 생성
//        FormEntity formEntity = FormEntity.builder()
//                .user(user)
//                .date(dto.getDate())
//                .status("Pending")
//                .build();
//
//        // WasteApplication 생성
//        WasteApplicationEntity wasteApplicationEntity
//                = WasteApplicationEntity.builder()
//                .waste(wasteEntity)
//                .form(formEntity)
//                .quantity(dto.getQuantity())
//                .build();
//        wasteApplicationRepository.save(wasteApplicationEntity);
//
//        Integer totalPrice = wasteEntity.getPrice() * dto.getQuantity();
//        formEntity.setTotalPrice(totalPrice);
//        formRepository.save(formEntity);
//
//        // 다 끝나고 나면 일단 Form 정보 반환
//        return FormDto.fromEntity(formEntity);
//    }

//    // 폼 생성
//    public FormDto createForm(CreateFormDto createFormDto) {
//        // Waste의 id를 찾아보고 없으면 예외 처리
////        WasteEntity waste = wasteRepo.findById(itemId)
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        // User의 이름으로 찾아보고 해당 user가 없으면 예외 처리
//        User user = userRepository.findByUsername(createFormDto.getUsername())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//        // FormEntity 객체 생성 및 저장
//        FormEntity formEntity = FormEntity.builder()
//                .user(user)
//                .status("UnPaid")
//                .build();
//
//        // 저장 후, 저장된 Entity를 dto로 반환
//        return FormDto.fromEntity(formRepository.save(formEntity));
//    }


    // 쓰레기 선택 후 신청서에 저장하기
    public void selectWasteToApply() {

    }



    // 기존 폼 리스트들 확인 확인
    public void checklists() {

    }

    // 폐기물 종류와 수량 선택
}
