package com.example.relaxworld.waste.dto;

import com.example.relaxworld.waste.entity.FormEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDto {
    private Long id;            // 폼의 ID
    private String userId;      // 신청한 유저의 PK
    private String date;        // 신청한 날짜 (버릴날짜 X)
    private String location;    // 어디에 버릴 지

    private Integer totalPrice; // 전체 가격은?
    private String status;      // 결제 상태는?
    private Long paymentId;     // 어떤 결제 수단으로 했는지?

    public static FormDto fromEntity(FormEntity entity) {
        return FormDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getUserId())
                .date(entity.getDate())
                .location(entity.getLocation())
                .totalPrice(entity.getTotalPrice())
                .status(entity.getStatus())
                .paymentId(entity.getPaymentId())
                .build();
    }
}