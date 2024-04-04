package com.example.relaxworld.waste.dto;

import com.example.relaxworld.waste.entity.ApplicationListEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationListDto {
    private String applyDate;           // apply 신청 날짜
    private String itemDescriptions;    // 신청 품목들
    private Integer quantity;            // 총 갯수
    private Integer totalPrice;          // 총 금액
    private String dueDate;             // 버릴 날짜
    private String location;            // 버릴 위치
    private String payment;             // 결제 수단

    public static ApplicationListDto fromEntity(ApplicationListEntity entity) {
        return ApplicationListDto.builder()
                .applyDate(entity.getApplyDate())
                .itemDescriptions(entity.getItemDescriptions())
                .quantity(entity.getQuantity())
                .totalPrice(entity.getTotalPrice())
                .dueDate(entity.getDueDate())
                .location(entity.getLocation())
                .payment(entity.getPayment())
                .build();
    }
}
