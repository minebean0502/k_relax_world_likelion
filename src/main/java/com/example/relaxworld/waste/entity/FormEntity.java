package com.example.relaxworld.waste.entity;

import com.example.relaxworld.pay.entity.Payment;
import com.example.relaxworld.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 폼의 ID PK

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;              // 신청한 유저의 PK

    @Setter
    private String date;            // 신청한 날짜 (버릴 날짜 X)
    @Setter
    private String location;        // 어디에 버릴 지
    @Setter
    private Integer totalPrice;     // 결제 해야 할 전체 금액은?
    @Setter
    private String status;          // 결제 상태는?
    @Setter
    // @OneToOne
    // @JoinColumn(name = "payment_Id")
    private Long PaymentId;         // 어떤 결제 수단으로 결제 했는가?

    @OneToMany(mappedBy = "form")
    private List<WasteApplicationEntity> wasteApplications = new ArrayList<>();
}
