package com.example.relaxworld.waste.dto;

import lombok.Data;

@Data
public class FinalizeApplicationDto {
    private Long formId;                    // 최종적으로 확정할 폼의 ID
    private String location;                // 최종적으로 버릴 위치
    private String date;                    // 최종 신청 날짜
}
