package com.animalchipping.api.controller;

import com.animalchipping.api.entity.Location;
import com.animalchipping.api.repository.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class LocationController {
    private final LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping("/locations/{pointId}")
    public ResponseEntity<Location> getLocationById(@PathVariable("pointId") Long pointId) {
        if (pointId == null || pointId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Location> locationOptional = locationRepository.findById(pointId);

        return locationOptional.map(location -> ResponseEntity.ok().body(location))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@Valid @RequestBody Location location) {
        if (!isValidCoordinates(location.getLatitude(), location.getLongitude())) {
            return ResponseEntity.badRequest().build();
        }

        Location savedLocation = locationRepository.save(location);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedLocation);
    }

    @PutMapping("/locations/{pointId}")
    public ResponseEntity<Location> updateLocation(@PathVariable("pointId") Long pointId,
                                                   @Valid @RequestBody Location locationFromRequest) {
        if (pointId == null || pointId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        if (!isValidCoordinates(locationFromRequest.getLatitude(), locationFromRequest.getLongitude())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Location> locationOptional = locationRepository.findByLatitudeAndLongitude(
                locationFromRequest.getLatitude(),
                locationFromRequest.getLongitude());

        if (locationOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        locationOptional = locationRepository.findById(pointId);

        if (locationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Location locationToUpdate = locationOptional.get();

        locationToUpdate.setLatitude(locationFromRequest.getLatitude());
        locationToUpdate.setLongitude(locationFromRequest.getLongitude());

        Location updatedLocation = locationRepository.save(locationToUpdate);

        return ResponseEntity.ok().body(updatedLocation);
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

