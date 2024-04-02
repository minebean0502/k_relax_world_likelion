package com.example.relaxworld.pay.repository;

import com.example.relaxworld.pay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
