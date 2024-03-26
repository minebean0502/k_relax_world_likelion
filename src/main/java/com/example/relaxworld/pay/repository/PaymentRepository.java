package com.example.relaxworld.pay.repository;

import com.example.relaxworld.pay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
