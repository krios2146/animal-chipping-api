package com.animalchipping.api.controller;

import com.animalchipping.api.entity.AnimalType;
import com.animalchipping.api.entity.dto.AnimalTypeDto;
import com.animalchipping.api.repository.AnimalTypeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/animals/types")
    public ResponseEntity<AnimalType> createAnimalType(@Valid @RequestBody AnimalTypeDto animalTypeDto) {
        if (animalTypeRepository.findByType(animalTypeDto.getType()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AnimalType animalType = new AnimalType();
        animalType.setType(animalTypeDto.getType());

        AnimalType savedAnimalType = animalTypeRepository.save(animalType);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimalType);
    }
}
