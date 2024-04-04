package com.example.relaxworld.waste.controller;

import com.example.relaxworld.waste.dto.WasteDto;
import com.example.relaxworld.waste.entity.WasteEntity;
import com.example.relaxworld.waste.service.ApplyFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/waste")
@RequiredArgsConstructor
public class ApplyFormViewController {
    private final ApplyFormService applyFormService;

    @GetMapping
    public String toWaste() {
        return "/waste/waste";
    }
    @GetMapping("/apply")
    public String toApply(Model model) {
        List<WasteDto> wasteItems = applyFormService.wasteItems();
        model.addAttribute("wastes", wasteItems);
        return "/waste/apply/apply";
    }

    @GetMapping("/apply/add-waste-item")
    public String toApplyAddWasteItem() {
        return "/waste/apply/add-waste-item";
    }
    @GetMapping("/apply/finalize")
    public String toApplyFinalize() {
        return "/waste/apply/finalize";
    }
    @GetMapping("/applications")
    public String toApplications() {
        return "/waste/application";
    }
//    @GetMapping("/applications")
//    public String toApplications() {
//        return "/waste/application/application";
//    }
//    @GetMapping("/applications/{id}")
//    public String toApplicationsId() {
//        return "/waste/application/application";
//    }
}