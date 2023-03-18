package com.animalchipping.api.repository;

import com.animalchipping.api.entity.Account;
import com.animalchipping.api.entity.Animal;
import com.animalchipping.api.entity.AnimalType;
import com.animalchipping.api.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {
    boolean existsByChipper(Account account);

    boolean existsByChippingLocation(Location location);

    @Query("SELECT CASE " +
            "WHEN COUNT(a) > 0 THEN true " +
            "ELSE false " +
            "END " +
            "FROM Animal a " +
            "WHERE EXISTS " +
            "(SELECT at FROM AnimalType at " +
            "WHERE at.type = :#{#type.type} " +
            "AND at.animal = a)")
    boolean existsByAnimalType(@Param("type") AnimalType type);
}
