package com.example.relaxworld.waste.dto;

import lombok.Data;

@Data
public class SelectWasteFormDto {
    private String username;    // 어떤 사람이
    private Long id;            // 어떤 쓰레기의 id와
    private String description; // 어떤 쓰레기의 이름을
    private Integer quantity;   // 몇개를 골랐는지
    private String date;        // 몇일로 해서
}
