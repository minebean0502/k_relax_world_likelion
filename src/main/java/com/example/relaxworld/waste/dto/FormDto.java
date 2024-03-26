package com.example.relaxworld.waste.dto;

import com.example.relaxworld.waste.entity.FormEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDto {
    private Long id;
    private String username;
    private String date;
    private Integer totalPrice;

    public static FormDto fromEntity(FormEntity entity) {
        return FormDto.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .date(entity.getDate())
                .totalPrice(entity.getTotalPrice())
                .build();
    }
}