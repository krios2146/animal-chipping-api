package com.dripchip.api.entity.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PostAnimalRequest extends AnimalRequest {
    @NotNull
    private List<Long> animalTypes;
}
