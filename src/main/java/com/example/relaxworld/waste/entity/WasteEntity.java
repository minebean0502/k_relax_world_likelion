package com.example.relaxworld.waste.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WasteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // PK
    @Setter
    private String description; // 어떤 쓰레기인지
    @Setter
    private String price;       // 가격이 얼마인지

    @OneToMany(mappedBy = "waste")
    private List<WasteApplicationEntity> wasteApplications = new ArrayList<>();
}
