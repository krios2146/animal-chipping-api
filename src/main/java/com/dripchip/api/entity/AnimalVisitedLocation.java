package com.dripchip.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "animal_visited_location")
public class AnimalVisitedLocation {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateTimeOfVisitLocationPoint;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    public AnimalVisitedLocation(LocalDateTime dateTimeOfVisitLocationPoint, Location location, Animal animal) {
        this.dateTimeOfVisitLocationPoint = dateTimeOfVisitLocationPoint;
        this.location = location;
        this.animal = animal;
    }
}
