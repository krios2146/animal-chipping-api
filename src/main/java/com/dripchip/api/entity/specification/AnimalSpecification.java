package com.dripchip.api.entity.specification;

import com.dripchip.api.entity.Animal;
import com.dripchip.api.entity.enums.Gender;
import com.dripchip.api.entity.enums.LifeStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AnimalSpecification {
    public static Specification<Animal> chippedBetween(String startDateString, String endDateString) {
        return new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

                if (startDateString == null && endDateString == null) {
                    return null;
                }

                if (startDateString != null && endDateString == null) {
                    LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("chippingDateTime"), startDate);
                }

                if (endDateString != null && startDateString == null) {
                    LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);
                    return criteriaBuilder.lessThanOrEqualTo(root.get("chippingDateTime"), endDate);
                }

                LocalDateTime startDate = LocalDateTime.parse(startDateString, formatter);
                LocalDateTime endDate = LocalDateTime.parse(endDateString, formatter);

                return criteriaBuilder.between(root.get("chippingDateTime"), startDate, endDate);
            }
        };
    }

    public static Specification<Animal> chippedBy(Long chipperId) {
        return new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (chipperId == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("chipperId"), chipperId);
            }
        };
    }

    public static Specification<Animal> chippedAt(Long chippingLocationId) {
        return new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (chippingLocationId == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("chippingLocationId"), chippingLocationId);
            }
        };
    }

    public static Specification<Animal> hasLifeStatus(String lifeStatus) {
        return new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (lifeStatus == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("lifeStatus"), LifeStatus.valueOf(lifeStatus));
            }
        };
    }

    public static Specification<Animal> hasGender(String gender) {
        return new Specification<Animal>() {
            @Override
            public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (gender == null) {
                    return null;
                }
                return criteriaBuilder.equal(root.get("gender"), Gender.valueOf(gender));
            }
        };
    }

}
