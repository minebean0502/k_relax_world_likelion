package com.example.relaxworld.user.repository;

import com.example.relaxworld.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUserId(String userid);

    Optional<User> findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);
}
