package com.example.relaxworld.waste.dto;

import com.example.relaxworld.waste.entity.WasteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteDto {
    private Long id;
    private String description;
    private Integer price;

    public static WasteDto fromEntity(WasteEntity entity) {
        return WasteDto.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .build();
    }
}
