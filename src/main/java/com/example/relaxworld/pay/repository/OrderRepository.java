package com.example.relaxworld.pay.repository;

import com.example.relaxworld.pay.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Form, Long> {
    @Query("select o from Form o" +
            " left join fetch o.payment p" +
            " left join fetch o.payUser u" +
            " where o.orderUid = :orderUid")
    Optional<Form> findOrderAndPaymentAndUseber(String orderUid);

    @Query("select o from Form o" +
            " left join fetch o.payment p" +
            " where o.orderUid = :orderUid")
    Optional<Form> findOrderAndPayment(String orderUid);
}
