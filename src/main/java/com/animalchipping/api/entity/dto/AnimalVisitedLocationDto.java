package com.animalchipping.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class AnimalVisitedLocationDto {
    private Long id;
    private OffsetDateTime dateTimeOfVisitLocationPoint;
    private Long locationPointId;
}
