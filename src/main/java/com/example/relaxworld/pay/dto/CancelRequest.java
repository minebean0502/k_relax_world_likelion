package com.example.relaxworld.pay.dto;

import com.example.relaxworld.pay.entity.Order;
import com.example.relaxworld.pay.entity.Payment;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CancelRequest {
    private String paymentUid; // 결제 고유 번호
    private String orderUid; // 주문 고유 번호

    @Builder
    public CancelRequest(String orderUid,String paymentUid){
        this.orderUid=orderUid;
        this.paymentUid=paymentUid;
    }
}