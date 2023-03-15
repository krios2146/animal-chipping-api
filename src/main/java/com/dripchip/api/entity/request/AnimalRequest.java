package com.dripchip.api.entity.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRequest {
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
