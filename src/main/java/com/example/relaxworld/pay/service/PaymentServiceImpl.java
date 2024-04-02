package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.dto.CancelRequest;
import com.example.relaxworld.pay.dto.PaymentCallbackRequest;
import com.example.relaxworld.pay.dto.RequestPayDto;
import com.example.relaxworld.pay.entity.Order;
import com.example.relaxworld.pay.entity.PaymentStatus;
import com.example.relaxworld.pay.repository.OrderRepository;
import com.example.relaxworld.pay.repository.PaymentRepository;
import com.example.relaxworld.pay.service.PaymentService;
import com.siot.IamportRestClient.Iamport;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.AuthData;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.AccessToken;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    public static final String API_URL = "https://api.iamport.kr";
    public static final String STATIC_API_URL = "localhost:8080";
    protected String apiKey = null;
    protected String apiSecret = null;
    protected String tierCode = null;
    protected Iamport iamport = null;

    @Override
    public RequestPayDto findRequestDto(String orderUid) {
        Order order = orderRepository.findOrderAndPaymentAndUseber(orderUid)
                .orElseThrow(() -> new IllegalArgumentException("주문이 없습니다."));
        return RequestPayDto.builder()
                .buyerName(order.getUser().getUsername())
                .buyerId(order.getUser().getUserId())
                .buyerPassword(order.getUser().getPassword())
                .paymentPrice(order.getPayment().getPrice())
                .itemName(order.getItemName())
                .orderUid(order.getOrderUid())
                .build();
    }

    @Override
    public IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request) {
        try {

            // 결제 단건 조회(아임포트)
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 주문내역 조회
            Order order = orderRepository.findOrderAndPayment(request.getOrderUid())
                    .orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));            // 결제 완료가 아니면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                orderRepository.delete(order);
                paymentRepository.delete(order.getPayment());
                throw new RuntimeException("결제 미완료");
            }
            // DB에 저장된 결제 금액
            Long price = order.getPayment().getPrice();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();
            // 결제 금액 검증
            if (iamportPrice != price) {
                // 주문, 결제 삭제
                orderRepository.delete(order);
                paymentRepository.delete(order.getPayment());
                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), false, new BigDecimal(iamportPrice)));
                throw new RuntimeException("결제금액 위변조 의심");
            }
            // 결제 상태 변경
            order.getPayment().changePaymentBySuccess(PaymentStatus.OK, iamportResponse.getResponse().getImpUid());
            return iamportResponse;
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}