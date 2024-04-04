package com.example.relaxworld.pay.entity;

//import com.siot.IamportRestClient.response.Payment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private String itemName;
    private String location;
    private String orderUid; // 주문 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PayUser payUser;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    public Form(Long price, String itemName, String location, String orderUid, PayUser payUser, Payment payment) {
        this.price = price;
        this.itemName = itemName;
        this.location=location;
        this.orderUid = orderUid;
        this.payUser = payUser;
        this.payment = payment;
    }
}
