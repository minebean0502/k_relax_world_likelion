package com.example.relaxworld.pay.repository;

import com.example.relaxworld.pay.entity.PayUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayUserRepository extends JpaRepository<PayUser,Long> {
}
