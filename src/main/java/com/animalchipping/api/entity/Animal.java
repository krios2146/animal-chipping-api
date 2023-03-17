package com.animalchipping.api.entity;

import com.animalchipping.api.entity.enums.Gender;
import com.animalchipping.api.entity.enums.LifeStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "animal")
    private Set<AnimalType> animalTypes;

    private Double weight;

    private Double length;

    private Double height;

    private Gender gender;

    private LifeStatus lifeStatus;

    private LocalDateTime chippingDateTime;

    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;

    @ManyToOne
    @JoinColumn(name = "chipping_location_id")
    private Location chippingLocation;

    @OneToMany(mappedBy = "animal")
    private List<AnimalVisitedLocation> visitedLocations;

    private LocalDateTime deathDateTime;
}
