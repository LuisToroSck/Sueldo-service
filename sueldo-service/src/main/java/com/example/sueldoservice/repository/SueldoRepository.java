package com.example.sueldoservice.repository;

import com.example.sueldoservice.entity.SueldoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SueldoRepository extends JpaRepository<SueldoEntity, Long> {
}