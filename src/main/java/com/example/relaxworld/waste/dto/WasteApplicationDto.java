package com.example.relaxworld.waste.dto;

import com.example.relaxworld.waste.entity.WasteApplicationEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WasteApplicationDto {
    // private Long id;            // 접수서의 몇번 ID로 (PK)
    private Long wasteId;       // 어떤 쓰레기를
    private Integer quantity;   // 몇개를 버릴지
    private Long formId;        // 어떤 폼에
    private String date;        // 몇일로 버릴지 신청

    public static WasteApplicationDto fromEntity(WasteApplicationEntity entity){
        return WasteApplicationDto.builder()
                // .id(entity.getId())
                .wasteId(entity.getWaste().getId())
                .formId(entity.getForm().getId())
                .quantity(entity.getQuantity())
                .date(entity.getDate())
                .build();
    }
}
