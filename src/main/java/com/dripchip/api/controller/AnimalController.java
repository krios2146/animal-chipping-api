package com.dripchip.api.controller;

import com.dripchip.api.entity.Animal;
import com.dripchip.api.entity.enums.Gender;
import com.dripchip.api.entity.enums.LifeStatus;
import com.dripchip.api.entity.specification.AnimalSpecification;
import com.dripchip.api.repository.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
public class AnimalController {
    private final AnimalRepository animalRepository;

    public AnimalController(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @GetMapping("/animals/{animalId}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long animalId) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animal = animalRepository.findById(animalId);

        return animal.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("animals/search")
    public ResponseEntity<List<Animal>> searchAnimals(
            @RequestParam(required = false) String startDateTimeParameter,
            @RequestParam(required = false) String endDateTimeParameter,
            @RequestParam(required = false) Long chipperId,
            @RequestParam(required = false) Long chippingLocationId,
            @RequestParam(required = false) String lifeStatus,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (size <= 0 || from < 0) {
            return ResponseEntity.badRequest().build();
        }

        if (chipperId != null && chipperId <= 0 ||
                chippingLocationId != null && chippingLocationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (startDateTimeParameter != null && !isValidDate(startDateTimeParameter) ||
                endDateTimeParameter != null && !isValidDate(endDateTimeParameter)) {
            return ResponseEntity.badRequest().build();
        }

        if (lifeStatus != null && !isValidLifeStatus(lifeStatus) ||
                gender != null && !isValidGender(gender)) {
            return ResponseEntity.badRequest().build();
        }

        Specification<Animal> spec = Specification.where(AnimalSpecification.chippedBetween(startDateTimeParameter, endDateTimeParameter))
                .and(AnimalSpecification.chippedBy(chipperId))
                .and(AnimalSpecification.chippedAt(chippingLocationId))
                .and(AnimalSpecification.hasLifeStatus(lifeStatus))
                .and(AnimalSpecification.hasGender(gender));

        Page<Animal> animals = animalRepository.findAll(spec, PageRequest.of(from, size));

        if (animals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(animals.getContent());
    }

    private static boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            formatter.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static boolean isValidGender(String gender) {
        try {
            Gender.valueOf(gender);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean isValidLifeStatus(String lifeStatus) {
        try {
            LifeStatus.valueOf(lifeStatus);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
