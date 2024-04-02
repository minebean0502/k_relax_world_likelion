package com.example.relaxworld.pay.repository;

import com.example.relaxworld.pay.entity.Order;
import com.example.relaxworld.pay.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("select p from Payment p" +
            " left join fetch p.order o" +
            " where p.paymentUid = :paymentUid")
    Optional<Payment> findPaymentAndOrder(String paymentUid);
}
