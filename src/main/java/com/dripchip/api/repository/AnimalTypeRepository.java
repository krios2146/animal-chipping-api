package com.dripchip.api.repository;

import com.dripchip.api.entity.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    Optional<AnimalType> findByType(String type);
}
