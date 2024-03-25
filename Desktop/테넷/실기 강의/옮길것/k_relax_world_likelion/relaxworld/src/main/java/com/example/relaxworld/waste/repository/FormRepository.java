package com.example.relaxworld.waste.repository;

import com.example.relaxworld.waste.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Long> {
    List<FormEntity> findAllById(Long id);
}
