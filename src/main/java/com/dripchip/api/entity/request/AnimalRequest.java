package com.dripchip.api.entity.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnimalRequest {
    @NotNull
    private List<Long> animalTypes;

    @NotNull
    private Double weight;

    @NotNull
    private Double length;

    @NotNull
    private Double height;

    @NotNull
    private String gender;

    @NotNull
    private Long chipperId;

    @NotNull
    private Long chippingLocationId;
}
