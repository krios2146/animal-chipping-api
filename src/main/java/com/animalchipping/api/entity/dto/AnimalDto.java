package com.animalchipping.api.entity.dto;

import com.animalchipping.api.entity.enums.Gender;
import com.animalchipping.api.entity.enums.LifeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AnimalDto {
    private Long id;
    private List<Long> animalTypes;
    private Double weight;
    private Double length;
    private Double height;
    private Gender gender;
    private LifeStatus lifeStatus;
    private OffsetDateTime chippingDateTime;
    private Long chipperId;
    private Long chippingLocationId;
    private List<Long> visitedLocations;
    private OffsetDateTime deathDateTime;
}
