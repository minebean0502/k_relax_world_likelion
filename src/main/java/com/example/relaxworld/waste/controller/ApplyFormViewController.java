package com.example.relaxworld.waste.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/waste")
@RequiredArgsConstructor
public class ApplyFormViewController {
    @GetMapping("/apply")
    public String toApply() {
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
        return "/waste/application/application-list";
    }
    @GetMapping("/applications/{id}")
    public String toApplicationsId() {
        return "/waste/application/application";
    }
}
