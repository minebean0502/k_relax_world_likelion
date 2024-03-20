package com.example.relaxworld.waste.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 결과적으로 얘도 필요하긴 함 ㅇㅇ

    @ManyToOne
    // @JoinColumn(name = "wasteId", referencedColumnName = "id")
    private WasteEntity waste;  // @ManyToOne
    @ManyToOne
    // @JoinColumn(name = "wasteId", referencedColumnName = "id")
    private FormEntity form;    // @ManyToOne

    private int quantity;       // 수량
}
