package com.dripchip.api.controller;

import com.dripchip.api.entity.AnimalType;
import com.dripchip.api.repository.AnimalTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AnimalTypeController {
    private final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeController(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @GetMapping("/animals/types/{typeId}")
    public ResponseEntity<AnimalType> getAnimalTypeById(@PathVariable Long typeId) {
        if (typeId == null || typeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<AnimalType> animalType = animalTypeRepository.findById(typeId);

        if (animalType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().body(animalType.get());
    }
}
