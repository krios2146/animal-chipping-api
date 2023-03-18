package com.animalchipping.api.controller;

import com.animalchipping.api.entity.*;
import com.animalchipping.api.entity.dto.AnimalDto;
import com.animalchipping.api.entity.dto.AnimalVisitedLocationDto;
import com.animalchipping.api.entity.enums.Gender;
import com.animalchipping.api.entity.enums.LifeStatus;
import com.animalchipping.api.entity.request.AnimalRequest;
import com.animalchipping.api.entity.request.PostAnimalRequest;
import com.animalchipping.api.entity.request.PutAnimalRequest;
import com.animalchipping.api.entity.specification.AnimalSpecification;
import com.animalchipping.api.repository.AccountRepository;
import com.animalchipping.api.repository.AnimalRepository;
import com.animalchipping.api.repository.AnimalTypeRepository;
import com.animalchipping.api.repository.LocationRepository;
import jakarta.validation.Valid;
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
import java.util.*;

@RestController
@RequestMapping("/animals")
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

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable Long animalId) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);

        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        AnimalDto animalDto = getDtoFrom(animal);

        return ResponseEntity.ok().body(animalDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimalDto>> searchAnimals(
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

        List<AnimalDto> animalDtoList = animals.stream().map(AnimalController::getDtoFrom).toList();

        return ResponseEntity.ok().body(animalDtoList);
    }

    @PostMapping
    public ResponseEntity<AnimalDto> createAnimal(@Valid @RequestBody PostAnimalRequest animalRequest) {
        if (animalRequest.getAnimalTypes().size() == 0) {
            return ResponseEntity.badRequest().build();
        }

        boolean isValidIds = animalRequest.getAnimalTypes().stream()
                .allMatch(id -> id > 0);

        if (!isValidIds) {
            return ResponseEntity.badRequest().build();
        }

        if (!isValidRequest(animalRequest)) {
            return ResponseEntity.badRequest().build();
        }

        List<AnimalType> animalTypeList = animalTypeRepository.findAllById(animalRequest.getAnimalTypes());

        if (animalTypeList.size() != animalRequest.getAnimalTypes().size()) {
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
        animal.setVisitedLocations(List.of());

        Animal savedAnimal = animalRepository.save(animal);

        AnimalDto animalDto = getDtoFrom(savedAnimal);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalDto);
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable Long animalId,
                                                  @Valid @RequestBody PutAnimalRequest animalRequest) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!isValidRequest(animalRequest)) {
            return ResponseEntity.badRequest().build();
        }

        if (!isValidLifeStatus(animalRequest.getLifeStatus())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        Optional<Account> accountOptional = accountRepository.findById(animalRequest.getChipperId());
        Optional<Location> locationOptional = locationRepository.findById(animalRequest.getChippingLocationId());

        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        Account chipper = accountOptional.get();
        Location chippingLocation = locationOptional.get();

        if (animal.getLifeStatus().equals(LifeStatus.DEAD) &&
                LifeStatus.valueOf(animalRequest.getLifeStatus()).equals(LifeStatus.ALIVE)) {
            return ResponseEntity.badRequest().build();
        }

        animal.setWeight(animalRequest.getWeight());
        animal.setLength(animalRequest.getLength());
        animal.setHeight(animalRequest.getHeight());
        animal.setGender(Gender.valueOf(animalRequest.getGender()));
        animal.setLifeStatus(LifeStatus.valueOf(animalRequest.getLifeStatus()));
        animal.setChipper(chipper);
        animal.setChippingLocation(chippingLocation);

        AnimalDto animalDto = getDtoFrom(animal);

        return ResponseEntity.ok().body(animalDto);
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable Long animalId) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);

        if (animalOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();

        if (!animal.getVisitedLocations().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        animalRepository.delete(animal);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalDto> addTypeToAnimal(@PathVariable Long animalId, @PathVariable Long typeId) {
        if (animalId == null || animalId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (typeId == null || typeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        Optional<AnimalType> typeOptional = animalTypeRepository.findById(typeId);

        if (animalOptional.isEmpty() || typeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        AnimalType animalType = typeOptional.get();

        if (animal.getAnimalTypes().contains(animalType)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Set<AnimalType> animalTypes = animal.getAnimalTypes();
        animalTypes.add(animalType);
        animal.setAnimalTypes(animalTypes);

        animalRepository.save(animal);

        AnimalDto animalDto = getDtoFrom(animal);

        return ResponseEntity.status(HttpStatus.CREATED).body(animalDto);
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<AnimalDto> updateTypeOfAnimal(@PathVariable Long animalId,
                                                        @RequestParam Long oldTypeId,
                                                        @RequestParam Long newTypeId) {
        if (animalId == null || animalId <= 0 ||
                oldTypeId == null || oldTypeId <= 0 ||
                newTypeId == null || newTypeId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        Optional<AnimalType> oldAnimalTypeOptional = animalTypeRepository.findById(oldTypeId);
        Optional<AnimalType> newAnimalTypeOptional = animalTypeRepository.findById(newTypeId);

        if (animalOptional.isEmpty() || oldAnimalTypeOptional.isEmpty() || newAnimalTypeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Animal animal = animalOptional.get();
        AnimalType oldAnimalType = oldAnimalTypeOptional.get();
        AnimalType newAnimalType = newAnimalTypeOptional.get();

        if (!animal.getAnimalTypes().contains(oldAnimalType)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (animal.getAnimalTypes().contains(newAnimalType)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Set<AnimalType> animalTypes = animal.getAnimalTypes();
        animalTypes.remove(oldAnimalType);
        animalTypes.add(newAnimalType);
        animal.setAnimalTypes(animalTypes);

        AnimalDto animalDto = getDtoFrom(animal);

        return ResponseEntity.ok().body(animalDto);
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

    private static AnimalDto getDtoFrom(Animal animal) {
        return new AnimalDto(
                animal.getId(),
                animal.getAnimalTypes().stream().map(AnimalType::getId).toList(),
                animal.getWeight(),
                animal.getLength(),
                animal.getHeight(),
                animal.getGender(),
                animal.getLifeStatus(),
                OffsetDateTime.of(animal.getChippingDateTime(), ZoneOffset.UTC),
                animal.getChipper().getId(),
                animal.getChippingLocation().getId(),
                new ArrayList<>(),
                null
        );
    }

    private static AnimalVisitedLocationDto getDtoFrom(AnimalVisitedLocation visitedLocation) {
        return new AnimalVisitedLocationDto(
                visitedLocation.getId(),
                OffsetDateTime.of(visitedLocation.getDateTimeOfVisitLocationPoint(), ZoneOffset.UTC),
                visitedLocation.getLocation().getId()
        );
    }

    private static boolean isValidRequest(AnimalRequest animalRequest) {
        if (animalRequest.getWeight() <= 0 || animalRequest.getLength() <= 0 || animalRequest.getHeight() <= 0) {
            return false;
        }

        if (!isValidGender(animalRequest.getGender())) {
            return false;
        }

        if (animalRequest.getChipperId() <= 0 || animalRequest.getChippingLocationId() <= 0) {
            return false;
        }
        return true;
    }
}
