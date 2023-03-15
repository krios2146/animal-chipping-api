package com.dripchip.api.controller;

import com.dripchip.api.entity.Location;
import com.dripchip.api.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

        if (locationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(locationOptional.get());
    }

}

