package com.animalchipping.api.repository;

import com.animalchipping.api.entity.Account;
import com.animalchipping.api.entity.Animal;
import com.animalchipping.api.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {
    boolean existsByChipper(Account account);
    boolean existsByChippingLocation(Location location);
}
