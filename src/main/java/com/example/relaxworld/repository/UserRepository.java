package com.example.relaxworld.repository;

import com.example.relaxworld.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
