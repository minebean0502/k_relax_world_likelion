package com.example.relaxworld.waste.service;

import com.example.relaxworld.AuthenticationFacade;
import com.example.relaxworld.user.entity.User;
import com.example.relaxworld.user.repository.UserRepository;
import com.example.relaxworld.waste.dto.FinalizeApplicationDto;
import com.example.relaxworld.waste.dto.FormDto;
import com.example.relaxworld.waste.dto.WasteApplicationDto;
import com.example.relaxworld.waste.dto.WasteItemDto;
import com.example.relaxworld.waste.entity.FormEntity;
import com.example.relaxworld.waste.entity.WasteApplicationEntity;
import com.example.relaxworld.waste.entity.WasteEntity;
import com.example.relaxworld.waste.repository.FormRepository;
import com.example.relaxworld.waste.repository.WasteApplicationRepository;
import com.example.relaxworld.waste.repository.WasteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyFormService {
    private final UserRepository userRepository;
    private final FormRepository formRepository;
    private final WasteRepository wasteRepository;
    private final WasteApplicationRepository wasteApplicationRepository;
    private final AuthenticationFacade facade;

    // 사용자가 서비스 선택 = 임시로 폼을 생성
    public FormDto createTempForm(String username) {
        // 일단 유저 정보 가져옴
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        // 임시로 form을 만든다
        FormEntity formEntity = FormEntity.builder()
                .user(user)
                .build();

        // 폼을 저장시키고 생성된 formDto 정보 반환
        return FormDto.fromEntity(formRepository.save(formEntity));
    }

    // 사용자가 쓰레기를 계속 담는다 = 장바구니
    public WasteApplicationDto addWasteItem(WasteItemDto wasteItemDto) {
        // 쓰레기의 Id로 쓰레기에 대한 정보 찾기
        WasteEntity wasteEntity = wasteRepository.findById(wasteItemDto.getWasteId())
                .orElseThrow(() -> new RuntimeException(
                        "Waste not found with id: " + wasteItemDto.getWasteId()));

        // 임시로 저장했던 Form에 대한 정보 찾기
        FormEntity formEntity = formRepository.findById(wasteItemDto.getFormId())
                .orElseThrow(() -> new RuntimeException(
                        "Form not found with id: " + wasteItemDto.getFormId()));

        // WasteApplicationEntity에 계속해서 정보 저장하기
        WasteApplicationEntity wasteApplicationEntity
                = WasteApplicationEntity.builder()
                .waste(wasteEntity)
                .form(formEntity)
                .quantity(wasteItemDto.getQuantity())
                .date(wasteItemDto.getDate())
                .build();

        // return wasteApplicationRepository.save(wasteApplication);
        return WasteApplicationDto.fromEntity(wasteApplicationRepository.save(wasteApplicationEntity));
    }


    // 사용자가 최종 신청서 완료 = 결정
    public FormDto finalizeApplication(FinalizeApplicationDto finalizeDto) {

        // 최종으로 신청할 userId을 찾는다 (user의 Pk를 사용해야함)
        User user = userRepository.findById(finalizeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 나중에는 Auth로 찾을수도 있음
        // 0328 타입 캐스팅 문제 생겼음, FormUserDetails 타입이 아니여서
        // principal 객체 타입 확인 -> 타입 캐스팅 필요 // 일단 FormUserDetails 잘못 만든것은 확실함 ㅇㅇ;
//        User currentUser = facade.extractUser();
//        System.out.println(currentUser.getUsername());
//        System.out.println(currentUser.getUserId());
//        System.out.println(currentUser.getPhoneNumber());

        // 폼도 찾아보아요
        FormEntity formEntity = formRepository.findByUserId(finalizeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Form not found with id: " + finalizeDto.getFormId()));
//        FormEntity formEntity = formRepository.findByUserId(Long.valueOf(finalizeDto.getUserId()))
//                .orElseThrow(() -> new RuntimeException("Form not found with id: " + finalizeDto.getFormId()));


        // 이후 totalPrice를 계산하는 로직
        /* 03-27 (stream으로 줄일 수 있을 것 같음)
        Integer totalPrice = 0;
        // totalPrice 계산 로직
        List<WasteApplicationEntity> wasteApplications = formEntity.getWasteApplications();
        for (WasteApplicationEntity wasteApplication : wasteApplications) {
            WasteEntity waste = wasteApplication.getWaste();
            Integer price = waste.getPrice();
            Integer quantity = wasteApplication.getQuantity();
            totalPrice += price * quantity;
        }
         */
        Integer totalPrice = formEntity.getWasteApplications().stream()
                .mapToInt(wasteApplication ->
                        wasteApplication.getWaste().getPrice() * wasteApplication.getQuantity())
                .sum();

        // 모든 순회가 끝나면 totalPrice에 대해 적립이 완전히 끝나있을 것
        // 마지막으로 totalPrice에 대한 정보를 저장하고, 리턴해줄 것
        formEntity.setTotalPrice(totalPrice);
        formEntity.setStatus("Pending");
        formEntity.setLocation(finalizeDto.getLocation());
        formEntity.setDate(finalizeDto.getDate());

        return FormDto.fromEntity(formRepository.save(formEntity));
    }

    // 기존 폼 리스트들 확인 확인
    public List<FormDto> readAllForms() {
        // 일단 어떤 유저인지 확인
        User user = facade.extractUser();

        List<FormDto> list = new ArrayList<>();
        for (FormEntity form : formRepository.findAll()) {
            FormDto formDto = FormDto.fromEntity(form);
            list.add(formDto);
        }
        return list;
    }

    // 리스트 하나만 확인
    public FormDto readOneForm(Long applicationId) {
        // 현재 사용자 정보 추출
        User user = facade.extractUser();

        // 사용자 ID와 폼 IID를 기반으로 폼 검색
        FormEntity formEntity = formRepository.findByIdAndUserId(applicationId, user.getId())
                .orElseThrow(()-> new EntityNotFoundException(
                        "Form not found with id: " + applicationId + " for user: " + user.getId()));
        // 찾은 FormEntity를 Dto로 변환
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





    // 폐기물 종류와 수량 선택
}
