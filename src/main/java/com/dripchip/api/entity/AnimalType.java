package com.dripchip.api.entity;

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

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

}
