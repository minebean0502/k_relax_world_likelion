package com.example.relaxworld.pay.service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.relaxworld.pay.dto.CancelRequest;
import com.example.relaxworld.pay.entity.Payment;
import com.example.relaxworld.pay.repository.OrderRepository;
import com.example.relaxworld.pay.repository.PaymentRepository;
import com.google.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;


import com.siot.IamportRestClient.IamportClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.net.ssl.HttpsURLConnection;



@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class RefundService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;


    public List<CancelRequest> checkPay(){
      List<CancelRequest> cancelRequestList= new ArrayList<>();
      for(Payment payment:paymentRepository.findAll()) {
          cancelRequestList.add(CancelRequest.builder()
                  .orderUid(payment.getOrder().getOrderUid())
                  .paymentUid(payment.getPaymentUid())
                  .build());

      }
        return cancelRequestList;

    }


   public CancelRequest cancelRequest(String id) {
       Payment payment= paymentRepository.findPaymentAndOrder(id)
               .orElseThrow(() -> new IllegalArgumentException("결제 내역이 없습니다."));
        return CancelRequest.builder()
                .paymentUid(payment.getPaymentUid())
                .orderUid(payment.getOrder().getOrderUid())
                .build();
   }
   public void deletePayment(String id){
    Payment payment=paymentRepository.findPaymentAndOrder(id)
            .orElseThrow(() -> new IllegalArgumentException("결제 내역이 없습니다."));
    paymentRepository.delete(payment);
   }

    public void refundRequest(String access_token, CancelRequest request) throws IOException {
        URL url = new URL("https://api.iamport.kr/payments/cancel");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");
        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);
        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);
        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("merchant_uid", request.getOrderUid());
//        json.addProperty("reason", reason);
        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();
        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        br.close();
        conn.disconnect();
        log.info("결제 취소 완료 : 주문 번호 {}", request.getOrderUid());
        deletePayment(request.getPaymentUid());

        }






    public String getToken(String apiKey, String secretKey) throws IOException {
        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type과 Accept 헤더 설정
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString()); // json 객체를 문자열 형태로 HTTP 요청 본문에 추가
        bw.flush(); // BufferedWriter 비우기
        bw.close(); // BufferedWriter 종료

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson(); // 응답 데이터를 자바 객체로 변환
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String accessToken = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close(); // BufferedReader 종료

        conn.disconnect(); // 연결 종료

        log.info("Iamport 엑세스 토큰 발급 성공 : {}", accessToken);
        return accessToken;
    }



}
