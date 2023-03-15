package com.dripchip.api.controller;

import com.dripchip.api.entity.AnimalVisitedLocation;
import com.dripchip.api.entity.specification.AnimalVisitedLocationSpecification;
import com.dripchip.api.repository.AnimalVisitedLocationRepository;
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

@RestController
public class AnimalVisitedLocationController {
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;

    public AnimalVisitedLocationController(AnimalVisitedLocationRepository animalVisitedLocationRepository) {
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
    }

    @GetMapping("/animals/{animalId}/locations")
    public ResponseEntity<List<AnimalVisitedLocation>> getAnimalLocationPoints(
            @PathVariable Long animalId,
            @RequestParam(required = false) String startDateTime,
            @RequestParam(required = false) String endDateTime,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (startDateTime != null && !isValidDate(startDateTime) ||
                endDateTime != null && !isValidDate(endDateTime)) {
            return ResponseEntity.badRequest().build();
        }

        if (size <= 0 || from < 0) {
            return ResponseEntity.badRequest().build();
        }

        Specification<AnimalVisitedLocation> spec = Specification
                .where(AnimalVisitedLocationSpecification.byAnimalId(animalId))
                .and(AnimalVisitedLocationSpecification.visitedBetween(startDateTime, endDateTime));

        Page<AnimalVisitedLocation> visitedLocations = animalVisitedLocationRepository.findAll(spec, PageRequest.of(from, size));

        if (visitedLocations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(visitedLocations.getContent());
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
}
