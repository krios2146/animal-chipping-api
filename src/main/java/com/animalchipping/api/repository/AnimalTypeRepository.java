package com.animalchipping.api.repository;

import com.animalchipping.api.entity.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {
    Optional<AnimalType> findByType(String type);
}
