package com.example.relaxworld.waste.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class WasteItemDto {
    private Long formId;          // 폼의 ID
    private Long id;              // 쓰레기의 ID
    private Integer quantity;     // 쓰레기의 양
    private String date;          // 쓰레기를 버릴 날짜
}
