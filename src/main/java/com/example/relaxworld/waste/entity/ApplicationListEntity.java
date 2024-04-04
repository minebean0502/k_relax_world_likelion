package com.example.relaxworld.waste.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 일단 PK
    private String applyDate;           // apply 신청 날짜
    private String itemDescriptions;    // 신청 품목들
    private Integer quantity;            // 총 갯수
    private Integer totalPrice;          // 총 금액
    private String dueDate;             // 버릴 날짜
    private String location;            // 버릴 위치
    private String payment;             // 결제 수단
}
