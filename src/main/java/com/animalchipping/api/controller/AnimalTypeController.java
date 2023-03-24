package com.animalchipping.api.controller;

import com.animalchipping.api.entity.AnimalType;
import com.animalchipping.api.entity.dto.AnimalTypeDto;
import com.animalchipping.api.repository.AnimalRepository;
import com.animalchipping.api.repository.AnimalTypeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/animals/types")
@CrossOrigin
public class AnimalTypeController {
    private final AnimalTypeRepository animalTypeRepository;
    private final AnimalRepository animalRepository;

    public AnimalTypeController(AnimalTypeRepository animalTypeRepository,
                                AnimalRepository animalRepository) {
        this.animalTypeRepository = animalTypeRepository;
        this.animalRepository = animalRepository;
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

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable Long typeId) {
        if (typeId == null || typeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<AnimalType> animalTypeOptional = animalTypeRepository.findById(typeId);

        if (animalTypeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AnimalType animalType = animalTypeOptional.get();

        if (animalRepository.existsByAnimalType(animalType)) {
            return ResponseEntity.badRequest().build();
        }

        animalTypeRepository.delete(animalType);

        return ResponseEntity.ok().build();
    }
}
