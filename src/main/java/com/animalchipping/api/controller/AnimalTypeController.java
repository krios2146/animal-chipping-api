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
@RequestMapping("/animals/types")
public class AnimalTypeController {
    private final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeController(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> getAnimalTypeById(@PathVariable Long typeId) {
        if (typeId == null || typeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<AnimalType> animalType = animalTypeRepository.findById(typeId);

        return animalType.map(type -> ResponseEntity.ok().body(type))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    // TODO: Use an `AnimalTypeRequest` for the request and an `AnimalTypeDto` for the response
    public ResponseEntity<AnimalType> createAnimalType(@Valid @RequestBody AnimalTypeDto animalTypeDto) {
        if (animalTypeRepository.findByType(animalTypeDto.getType()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AnimalType animalType = new AnimalType();
        animalType.setType(animalTypeDto.getType());

        AnimalType savedAnimalType = animalTypeRepository.save(animalType);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimalType);
    }

    @PutMapping("{typeId}")
    // TODO: Use an `AnimalTypeRequest` for the request and an `AnimalTypeDto` for the response
    public ResponseEntity<AnimalType> updateAnimalType(@PathVariable Long typeId, @Valid @RequestBody AnimalTypeDto animalTypeDto) {
        if (typeId == null || typeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<AnimalType> animalTypeOptional = animalTypeRepository.findById(typeId);

        if (animalTypeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (animalTypeRepository.findByType(animalTypeDto.getType()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AnimalType animalType = animalTypeOptional.get();
        animalType.setType(animalTypeDto.getType());

        AnimalType updatedAnimalType = animalTypeRepository.save(animalType);

        return ResponseEntity.ok().body(updatedAnimalType);
    }
}
