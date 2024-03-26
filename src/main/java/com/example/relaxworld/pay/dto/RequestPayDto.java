package com.example.relaxworld.pay.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPayDto {
    private String orderUid;
    private String itemName;
    private String buyerName;
    private Long paymentPrice;
    private String buyerId;
    private String buyerPassword;

    @Builder
    public RequestPayDto(String orderUid, String itemName, String buyerName, Long paymentPrice, String buyerId,String buyerPassword) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerId=buyerId;
        this.buyerPassword=buyerPassword;
    }
}
