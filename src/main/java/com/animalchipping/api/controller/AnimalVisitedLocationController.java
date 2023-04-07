package com.animalchipping.api.controller;

import com.animalchipping.api.entity.Animal;
import com.animalchipping.api.entity.AnimalVisitedLocation;
import com.animalchipping.api.entity.Location;
import com.animalchipping.api.entity.dto.AnimalVisitedLocationDto;
import com.animalchipping.api.entity.enums.LifeStatus;
import com.animalchipping.api.entity.specification.AnimalVisitedLocationSpecification;
import com.animalchipping.api.repository.AnimalRepository;
import com.animalchipping.api.repository.AnimalVisitedLocationRepository;
import com.animalchipping.api.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/animals")
@CrossOrigin
public class AnimalVisitedLocationController {
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final AnimalRepository animalRepository;
    private final LocationRepository locationRepository;

    public AnimalVisitedLocationController(AnimalVisitedLocationRepository animalVisitedLocationRepository, AnimalRepository animalRepository,
                                           LocationRepository locationRepository) {
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.animalRepository = animalRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<List<AnimalVisitedLocationDto>> getAnimalLocationPoints(
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

        List<AnimalVisitedLocationDto> visitedLocationDtoList = visitedLocations.getContent().stream()
                .map(AnimalVisitedLocationController::getDtoFrom)
                .toList();

        return ResponseEntity.ok().body(visitedLocationDtoList);
    }

    @PostMapping("/{animalId}/locations/{locationId}")
    public ResponseEntity<AnimalVisitedLocationDto> addAnimalVisitedLocation(@PathVariable Long animalId,
                                                                             @PathVariable Long locationId) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (locationId == null || locationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        Optional<Location> locationOptional = locationRepository.findById(locationId);

        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        Location location = locationOptional.get();

        if (animal.getLifeStatus().equals(LifeStatus.DEAD)) {
            return ResponseEntity.badRequest().build();
        }

        if (animal.getChippingLocation().equals(location)) {
            return ResponseEntity.badRequest().build();
        }

        AnimalVisitedLocation animalVisitedLocation = new AnimalVisitedLocation(
                LocalDateTime.now(),
                location,
                animal
        );

        animalVisitedLocation = animalVisitedLocationRepository.save(animalVisitedLocation);

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        visitedLocations.add(animalVisitedLocation);
        animal.setVisitedLocations(visitedLocations);

        animalRepository.save(animal);

        AnimalVisitedLocationDto animalVisitedLocationDto = getDtoFrom(animalVisitedLocation);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalVisitedLocationDto);
    }

    @PutMapping("/{animalId}/locations")
    public ResponseEntity<AnimalVisitedLocationDto> updateVisitedLocation(@PathVariable Long animalId,
                                                                          @RequestParam Long visitedLocationId,
                                                                          @RequestParam Long locationId) {
        if (animalId == null || animalId <= 0 ||
                visitedLocationId == null || visitedLocationId <= 0 ||
                locationId == null || locationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        Optional<AnimalVisitedLocation> visitedLocationOptional = animalVisitedLocationRepository.findById(visitedLocationId);

        if (animalOptional.isEmpty() || locationOptional.isEmpty() || visitedLocationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        AnimalVisitedLocation animalVisitedLocation = visitedLocationOptional.get();
        Location location = locationOptional.get();

        if (!animal.getVisitedLocations().contains(animalVisitedLocation)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (animalVisitedLocation.getLocation().equals(location)) {
            return ResponseEntity.badRequest().build();
        }

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        visitedLocations.remove(animalVisitedLocation);
        visitedLocations.add(new AnimalVisitedLocation(
                LocalDateTime.now(),
                location,
                animal
        ));
        animal.setVisitedLocations(visitedLocations);

        AnimalVisitedLocationDto visitedLocationDto = getDtoFrom(animalVisitedLocation);

        return ResponseEntity.ok().body(visitedLocationDto);
    }

    @DeleteMapping("/{animalId}/locations/{visitedLocationId}")
    public ResponseEntity<?> deleteVisitedLocation(@PathVariable Long animalId, @PathVariable Long visitedLocationId) {
        if (animalId == null || animalId <= 0 ||
                visitedLocationId == null || visitedLocationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        Optional<AnimalVisitedLocation> visitedLocationOptional = animalVisitedLocationRepository.findById(visitedLocationId);

        if (animalOptional.isEmpty() || visitedLocationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        AnimalVisitedLocation animalVisitedLocation = visitedLocationOptional.get();

        if (!animal.getVisitedLocations().contains(animalVisitedLocation)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<AnimalVisitedLocation> visitedLocations = animal.getVisitedLocations();
        visitedLocations.remove(animalVisitedLocation);
        animal.setVisitedLocations(visitedLocations);

        return ResponseEntity.ok().build();
    }

    private static AnimalVisitedLocationDto getDtoFrom(AnimalVisitedLocation visitedLocation) {
        return new AnimalVisitedLocationDto(
                visitedLocation.getId(),
                OffsetDateTime.of(visitedLocation.getDateTimeOfVisitLocationPoint(), ZoneOffset.UTC),
                visitedLocation.getLocation().getId()
        );
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
