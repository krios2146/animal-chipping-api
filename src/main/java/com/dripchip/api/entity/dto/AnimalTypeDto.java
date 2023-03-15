package com.dripchip.api.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimalTypeDto {
    @NotNull
    @NotBlank
    private String type;
}
