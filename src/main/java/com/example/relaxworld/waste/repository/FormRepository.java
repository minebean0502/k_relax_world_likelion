package com.example.relaxworld.waste.repository;

import com.example.relaxworld.waste.entity.FormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends JpaRepository<FormEntity, Long> {
    Optional<FormEntity> findByUserId(Long userId);

    List<FormEntity> findAllByUserId(Long userId);

    Optional<FormEntity> findByIdAndUserId(Long formId, Long userId);

}
