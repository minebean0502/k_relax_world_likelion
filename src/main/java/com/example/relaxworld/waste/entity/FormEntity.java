package com.example.relaxworld.waste.entity;

import com.example.relaxworld.entity.User;
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
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;  // 나중에 UserEntity 타입 변환
                            // private UserEntity user;
    @Setter
    private String date;      // 일단 날짜 표시는 이렇게 둠
    @Setter
    private String location;
    @Setter
    private String status;
    @Setter
    private Integer totalPrice;

    @Setter
    // @OneToOne
    // @JoinColumn(name = "paymentId")
    private Long paymentId; // 나중에 PaymentEntity 타입 변환
                            // private PaymentEntity payment

    @OneToMany(mappedBy = "form")
    private List<WasteApplicationEntity> wasteApplications = new ArrayList<>();
}
