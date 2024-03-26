package com.example.relaxworld.waste.dto;

import lombok.Data;

import java.util.List;

@Data
public class FinalizeApplicationDto {
    private Long formId;              // 최종적으로 확정할 폼의 ID
    private String username;          // 사용자 이름
    private List<WasteItemDto> wasteItems; // 폐기물 항목 리스트
}
