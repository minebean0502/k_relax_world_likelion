package com.example.relaxworld.service;

import com.example.relaxworld.entity.Order;
import com.example.relaxworld.entity.Payment;
import com.example.relaxworld.entity.PaymentStatus;
import com.example.relaxworld.entity.User;
import com.example.relaxworld.repository.OrderRepository;
import com.example.relaxworld.repository.PaymentRepository;
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
    public Order autoOrder(User user) {
        // 임시 결제내역 생성
        Payment payment = Payment.builder()
                .price(1L)
                .status(PaymentStatus.READY)
                .build();
        paymentRepository.save(payment);
        // 주문 생성
        Order order = Order.builder()
                .user(user)
                .price(1L)
                .itemName("침대")
                .orderUid(UUID.randomUUID().toString())
                .payment(payment)
                .build();
        return orderRepository.save(order);
    }
}
