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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "waste_Id", referencedColumnName = "id")
    private WasteEntity waste;

    @ManyToOne
    @JoinColumn(name = "form_Id", referencedColumnName = "id")
    private FormEntity form;

    private String date;            // 선택한 날짜
    private Integer quantity;       // 수량
}
