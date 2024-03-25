package com.example.relaxworld.waste.repository;

import com.example.relaxworld.waste.entity.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<WasteEntity, Long> {

}
