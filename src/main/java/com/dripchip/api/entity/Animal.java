package com.dripchip.api.entity;

import com.dripchip.api.entity.enums.Gender;
import com.dripchip.api.entity.enums.LifeStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "animal")
public class Animal {
    @Id
    @GeneratedValue
    private Long id;
    private List<Long> animalTypes;
    private Double weight;
    private Double length;
    private Double height;
    private Gender gender;
    private LifeStatus lifeStatus;
    private LocalDateTime chippingDateTime;
    private Integer chipperId;
    private Long chippingLocationId;
    private List<Long> visitedLocations;
    private LocalDateTime deathDateTime;
}
