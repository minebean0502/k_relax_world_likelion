package com.example.relaxworld.waste.repository;

import com.example.relaxworld.waste.entity.WasteApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WasteApplicationRepository extends JpaRepository<WasteApplicationEntity, Long> {

    List<WasteApplicationEntity> findByFormId(Long formId);
    List<WasteApplicationEntity> findAllByFormId(Long formId);
}
