package com.example.relaxworld.waste.dto;

import com.example.relaxworld.waste.entity.FormEntity;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDto {
    private Long id;
    private String userId;  // 일단 이것도 수정 할 것 같음
    private Date date;      // 날짜 형식 일단 이걸로 둠
    private String location;
    private String status;
    private int totalPrice;
    private Long paymentId; // 나중에 PaymentEntity 타입 변환

    public static FormDto fromEntity(FormEntity entity) {
        return FormDto.builder()
                .userId(entity.getUserId())
                .date(entity.getDate())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .totalPrice(entity.getTotalPrice())
                .paymentId(entity.getPaymentId())
                .build();
    }
}