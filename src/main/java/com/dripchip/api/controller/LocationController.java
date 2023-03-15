package com.dripchip.api.controller;

import com.dripchip.api.entity.Location;
import com.dripchip.api.repository.LocationRepository;
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

