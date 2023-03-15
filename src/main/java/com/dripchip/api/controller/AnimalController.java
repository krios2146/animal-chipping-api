package com.dripchip.api.controller;

import com.dripchip.api.entity.Animal;
import com.dripchip.api.repository.AnimalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AnimalController {
    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping("/animals/{animalId}")
    public ResponseEntity<Animal> getAnimal(@PathVariable Long animalId) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animal = animalRepository.findById(animalId);

        return animal.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
