package com.example.relaxworld.service;

import com.example.relaxworld.dto.PaymentCallbackRequest;
import com.example.relaxworld.dto.RequestPayDto;
import com.example.relaxworld.entity.Order;
import com.example.relaxworld.entity.PaymentStatus;
import com.example.relaxworld.repository.OrderRepository;
import com.example.relaxworld.repository.PaymentRepository;
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
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    public static final String API_URL = "https://api.iamport.kr";
    public static final String STATIC_API_URL = "https://static-api.iamport.kr";
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
                    .orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));
            // 결제 완료가 아니면
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
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));
                throw new RuntimeException("결제금액 위변조 의심");
            }
            // 결제 상태 변경
            order.getPayment().changePaymentBySuccess(PaymentStatus.OK, iamportResponse.getResponse().getImpUid());
            return iamportResponse;
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IamportResponse<AccessToken> getAuth() throws IamportResponseException, IOException {
        Call<IamportResponse<AccessToken>> call = this.iamport.token( new AuthData(this.apiKey, this.apiSecret) );
        Response<IamportResponse<AccessToken>> response = call.execute();


        return response.body();
    }

    public IamportResponse<Payment> cancelPaymentByImpUid(CancelData cancelData) throws IamportResponseException, IOException {
        AccessToken auth = getAuth().getResponse();
        Call<IamportResponse<Payment>> call = this.iamport.cancel_payment(auth.getToken(), cancelData);

        Response<IamportResponse<Payment>> response = call.execute();
        return response.body();
    }
    @Override
    public IamportResponse<Payment> PaymentCancel(PaymentCallbackRequest request) throws IamportResponseException, IOException {
        IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
        iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true));
        return iamportResponse;
    }
}
