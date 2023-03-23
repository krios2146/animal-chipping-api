package com.animalchipping.api.controller;

import com.animalchipping.api.entity.Location;
import com.animalchipping.api.repository.AnimalRepository;
import com.animalchipping.api.repository.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/locations")
@CrossOrigin
public class LocationController {
    private final LocationRepository locationRepository;
    private final AnimalRepository animalRepository;

    public LocationController(LocationRepository locationRepository,
                              AnimalRepository animalRepository) {
        this.locationRepository = locationRepository;
        this.animalRepository = animalRepository;
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable("locationId") Long locationId) {
        if (locationId == null || locationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Location> locationOptional = locationRepository.findById(locationId);

        return locationOptional.map(location -> ResponseEntity.ok().body(location))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@Valid @RequestBody Location location) {
        if (!isValidCoordinates(location.getLatitude(), location.getLongitude())) {
            return ResponseEntity.badRequest().build();
        }

        Location savedLocation = locationRepository.save(location);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocation);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<Location> updateLocation(@PathVariable("locationId") Long locationId,
                                                   @Valid @RequestBody Location locationFromRequest) {
        if (locationId == null || locationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!isValidCoordinates(locationFromRequest.getLatitude(), locationFromRequest.getLongitude())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Location> locationOptional = locationRepository.findById(locationId);

        if (locationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        locationOptional = locationRepository.findByLatitudeAndLongitude(
                locationFromRequest.getLatitude(),
                locationFromRequest.getLongitude()
        );

        if (locationOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Location locationToUpdate = locationOptional.get();

        locationToUpdate.setLatitude(locationFromRequest.getLatitude());
        locationToUpdate.setLongitude(locationFromRequest.getLongitude());

        Location updatedLocation = locationRepository.save(locationToUpdate);

        return ResponseEntity.ok().body(updatedLocation);
    }

    @DeleteMapping("{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) {
        if (locationId == null || locationId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Location> locationOptional = locationRepository.findById(locationId);

        if (locationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Location location = locationOptional.get();

        if (animalRepository.existsByChippingLocation(location)) {
            return ResponseEntity.badRequest().build();
        }

        locationRepository.delete(location);

        return ResponseEntity.ok().build();
    }

    private static boolean isValidCoordinates(Double lat, Double lon) {
        if (lat < -90 || lat > 90) {
            return false;
        }
        if (lon < -180 || lon > 180) {
            return false;
        }
        return true;
    }
}

