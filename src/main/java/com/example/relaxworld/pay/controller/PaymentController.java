package com.example.relaxworld.pay.controller;

import com.example.relaxworld.pay.dto.PaymentCallbackRequest;
import com.example.relaxworld.pay.dto.RequestPayDto;
import com.example.relaxworld.pay.service.PaymentService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.AuthData;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/{id}")
    public String paymentPage(@PathVariable(name = "id", required = false) String id,
                              Model model) {
        RequestPayDto requestDto = paymentService.findRequestDto(id);
        model.addAttribute("requestDto", requestDto);
        return "payment";
    }
    @ResponseBody
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<Payment>> validationPayment(
            @RequestBody PaymentCallbackRequest request) {
        IamportResponse<Payment> iamportResponse = paymentService.paymentByCallback(request);

        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }
    @GetMapping("payment/{id}/cancel")
    public String CancelPage(){
        return "cancel";
    }
    @ResponseBody
    @PostMapping("/cancel")
    public ResponseEntity<IamportResponse<Payment>> Cancel(
            @RequestBody PaymentCallbackRequest request) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse= paymentService.PaymentCancel(request);
        log.info("결제 실패={}",iamportResponse.getResponse().toString());
        return new ResponseEntity<>(iamportResponse,HttpStatus.OK);
    }
    @PostMapping("/users/getToken")
    public ResponseEntity<IamportResponse<AccessToken>> token(
            @RequestBody AuthData auth) {
        return new ResponseEntity<>(new IamportResponse<>(),HttpStatus.OK);
    }
    @PostMapping("/payments/cancel")
    public ResponseEntity<IamportResponse<Payment>> cancel_payment(
            @RequestHeader("Authorization") String token,
            @RequestBody CancelData cancel_data
    ) {
        return new ResponseEntity<>(new IamportResponse<>(),HttpStatus.OK);
    }
    @GetMapping("/success-payment")
    public String successPaymentPage() {
        return "success-payment";
    }

    @GetMapping("/fail-payment")
    public String failPaymentPage() {
        return "fail-payment";
    }

}
