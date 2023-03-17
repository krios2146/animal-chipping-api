package com.animalchipping.api.repository;

import com.animalchipping.api.entity.AnimalVisitedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnimalVisitedLocationRepository extends JpaRepository<AnimalVisitedLocation, Long>, JpaSpecificationExecutor<AnimalVisitedLocation> {

}
