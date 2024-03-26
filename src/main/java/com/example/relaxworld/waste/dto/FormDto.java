package com.example.relaxworld.waste.dto;

import com.example.relaxworld.entity.User;
import com.example.relaxworld.waste.entity.FormEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDto {
    private Long id;
    private User user;  // 일단 이것도 수정 할 것 같음
    private String date;      // 날짜 형식 일단 이걸로 둠
    private String location;
    private String status;
    private int totalPrice;
    private Long paymentId; // 나중에 PaymentEntity 타입 변환

    public static FormDto fromEntity(FormEntity entity) {
        return FormDto.builder()
                .user(entity.getUser())
                .date(entity.getDate())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .totalPrice(entity.getTotalPrice())
                .paymentId(entity.getPaymentId())
                .build();
    }
}