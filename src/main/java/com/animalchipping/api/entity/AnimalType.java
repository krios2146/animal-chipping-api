package com.animalchipping.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "animal_type")
public class AnimalType {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
}
