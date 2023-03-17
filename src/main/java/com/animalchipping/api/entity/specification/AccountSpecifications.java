package com.animalchipping.api.entity.specification;

import com.animalchipping.api.entity.Account;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecifications {
    public static Specification<Account> firstNameContainsIgnoreCase(String firstName) {
        return new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (firstName == null) {
                    return null;
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
            }
        };
    }

    public static Specification<Account> lastNameContainsIgnoreCase(String lastName) {
        return new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (lastName == null) {
                    return null;
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
            }
        };
    }

    public static Specification<Account> emailContainsIgnoreCase(String email) {
        return new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (email == null) {
                    return null;
                }
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
            }
        };
    }
}
