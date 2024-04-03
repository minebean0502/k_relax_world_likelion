package com.example.relaxworld.waste.repository;

import com.example.relaxworld.waste.entity.WasteEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<WasteEntity, Long> {
    Page<WasteEntity> findAll(Pageable pageable);
}
