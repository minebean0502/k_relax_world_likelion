package com.example.relaxworld.pay.controller;


import java.io.IOException;
import java.util.List;

import com.example.relaxworld.pay.dto.CancelRequest;
import com.example.relaxworld.pay.service.PaymentService;
import com.example.relaxworld.pay.service.RefundService;
import com.siot.IamportRestClient.IamportClient;

import com.siot.IamportRestClient.response.*;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@Slf4j
@RequiredArgsConstructor
public class RefundController {
    private final RefundService refundService;

//    private IamportClient iamportClient;

    private String apiKey = "2405650706273856";


    private String secretKey = "15RrguOXoJjH5ABmQsuTnI3MKMkSplHzZQOsGwubzkv18hXGUQRmd8wwxCJS9IWFl6RQm0M2wYZFg3QN";

/*
    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }
*/

    @GetMapping("/cancel/")
    public String CancelPage() {

        return "/cancel";
    }
    @ResponseBody
    @PostMapping("/cancel/check")
    public List<CancelRequest> getPayid() {
        return refundService.checkPay();
    }
    @ResponseBody
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable(name = "id") String id
    ) throws IOException {

        String token = refundService.getToken(apiKey, secretKey);
        CancelRequest cancelRequest = refundService.cancelRequest(id);
        refundService.refundRequest(token, cancelRequest);
        return ResponseEntity.ok().body("{\"message\": \"환불성공\"}");
    }




}

