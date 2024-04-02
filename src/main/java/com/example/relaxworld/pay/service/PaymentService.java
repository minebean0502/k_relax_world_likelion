package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.dto.CancelRequest;
import com.example.relaxworld.pay.dto.PaymentCallbackRequest;
import com.example.relaxworld.pay.dto.RequestPayDto;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;

public interface PaymentService {
    // 결제 요청 데이터 조회
    RequestPayDto findRequestDto(String orderUid);
    // 결제(콜백)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request);


}
