package com.dripchip.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AnimalDto {
    private Set<Long> animalTypesId;
    private Double weight;
    private Double length;
    private Double height;
    private String gender;
    private Long chipperId;
    private Long chippingLocationId;
}
