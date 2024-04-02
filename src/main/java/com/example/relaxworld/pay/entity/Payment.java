package com.example.relaxworld.pay.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private PaymentStatus status;
    private String paymentUid; // 결제 고유 번호
    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Payment(Long price, PaymentStatus status, String paymentUid,Order order) {
        this.price = price;
        this.status = status;
        this.paymentUid=paymentUid;
        this.order=order;

    }
    public void changePaymentBySuccess(PaymentStatus status, String paymentUid) {
        this.status = status;
        this.paymentUid = paymentUid;
    }
}
