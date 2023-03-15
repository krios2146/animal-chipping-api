package com.dripchip.api.controller;

import com.dripchip.api.entity.*;
import com.dripchip.api.entity.dto.AnimalDto;
import com.dripchip.api.entity.enums.Gender;
import com.dripchip.api.entity.enums.LifeStatus;
import com.dripchip.api.entity.specification.AnimalSpecification;
import com.dripchip.api.repository.AccountRepository;
import com.dripchip.api.repository.AnimalRepository;
import com.dripchip.api.repository.AnimalTypeRepository;
import com.dripchip.api.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class AnimalController {
    private final AnimalRepository animalRepository;
    private final AnimalTypeRepository animalTypeRepository;
    private final AccountRepository accountRepository;
    private final LocationRepository locationRepository;

    public AnimalController(AnimalRepository animalRepository, AnimalTypeRepository animalTypeRepository, AccountRepository accountRepository, LocationRepository locationRepository) {
        this.animalRepository = animalRepository;
        this.animalTypeRepository = animalTypeRepository;
        this.accountRepository = accountRepository;
        this.locationRepository = locationRepository;
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

    @PostMapping
    public ResponseEntity<Animal> createAnimal(@RequestBody AnimalDto animalRequest) {
        if (animalRequest.getAnimalTypesId().size() == 0) {
            return ResponseEntity.badRequest().build();
        }

        if (animalRequest.getWeight() <= 0 || animalRequest.getLength() <= 0 || animalRequest.getHeight() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!isValidGender(animalRequest.getGender())) {
            return ResponseEntity.badRequest().build();
        }

        if (animalRequest.getChipperId() <= 0 || animalRequest.getChippingLocationId() <= 0) {
            return ResponseEntity.badRequest().build();
        }

        List<AnimalType> animalTypeList = animalTypeRepository.findAllById(animalRequest.getAnimalTypesId());

        if (animalTypeList.size() != animalRequest.getAnimalTypesId().size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Set<AnimalType> animalTypeSet = new HashSet<>(animalTypeList);

        Optional<Account> chipperOptional = accountRepository.findById(animalRequest.getChipperId());
        Optional<Location> locationOptional = locationRepository.findById(animalRequest.getChippingLocationId());

        if (chipperOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Account chipper = chipperOptional.get();
        Location chippingLocation = locationOptional.get();

        Animal animal = new Animal();
        animal.setAnimalTypes(animalTypeSet);
        animal.setWeight(animalRequest.getWeight());
        animal.setLength(animalRequest.getLength());
        animal.setHeight(animalRequest.getHeight());
        animal.setGender(Gender.valueOf(animalRequest.getGender()));
        animal.setLifeStatus(LifeStatus.ALIVE);
        animal.setChippingDateTime(LocalDateTime.now());
        animal.setChipper(chipper);
        animal.setChippingLocation(chippingLocation);
        animal.setVisitedLocations(List.of(new AnimalVisitedLocation(LocalDateTime.now(), chippingLocation, animal)));

        Animal savedAnimal = animalRepository.save(animal);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnimal);
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
