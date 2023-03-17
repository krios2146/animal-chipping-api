package com.animalchipping.api.entity.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PutAnimalRequest extends AnimalRequest {
    @NotNull
    private String lifeStatus;
}
