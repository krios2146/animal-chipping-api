package com.dripchip.api.entity.specification;

import com.dripchip.api.entity.AnimalVisitedLocation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnimalVisitedLocationSpecification {
    public static Specification<AnimalVisitedLocation> byAnimalId(Long animalId) {
        return new Specification<AnimalVisitedLocation>() {
            @Override
            public Predicate toPredicate(Root<AnimalVisitedLocation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (animalId == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("animal").get("id"), animalId);
            }
        };
    }

    public static Specification<AnimalVisitedLocation> visitedBetween(String startDateString, String endTimeString) {
        return new Specification<AnimalVisitedLocation>() {
            @Override
            public Predicate toPredicate(Root<AnimalVisitedLocation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                if (startDateString == null && endTimeString == null) {
                    return null;
                }

                if (startDateString != null && endTimeString == null) {
                    LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("dateTimeOfVisitLocationPoint"), startDate);
                }

                if (endTimeString != null && startDateString == null) {
                    LocalDateTime endDate = LocalDateTime.parse(endTimeString, formatter);
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("dateTimeOfVisitLocationPoint"), endDate);
                }

                LocalDateTime endDate = LocalDateTime.parse(endTimeString, formatter);
                LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);

                return criteriaBuilder.between(root.get("dateTimeOfVisitLocationPoint"), startDate, endDate);
            }
        };
    }
}
