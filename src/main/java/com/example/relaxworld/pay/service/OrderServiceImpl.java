package com.example.relaxworld.pay.service;

import com.example.relaxworld.pay.entity.Form;
import com.example.relaxworld.pay.entity.Payment;
import com.example.relaxworld.pay.entity.PaymentStatus;
import com.example.relaxworld.pay.entity.PayUser;
import com.example.relaxworld.pay.repository.OrderRepository;
import com.example.relaxworld.pay.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public Form autoOrder(PayUser payUser) {
        // 임시 결제내역 생성
        Payment payment = Payment.builder()
                .price(1L)
                .status(PaymentStatus.READY)
                .build();
        paymentRepository.save(payment);
        // 주문 생성
        Form order = Form.builder()
                .payUser(payUser)
                .price(1L)
                .itemName("침대")
                .orderUid(UUID.randomUUID().toString())
                .payment(payment)
                .build();
        return orderRepository.save(order);
    }
}
