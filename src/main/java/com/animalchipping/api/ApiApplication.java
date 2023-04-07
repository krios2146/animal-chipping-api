package com.animalchipping.api;

import com.animalchipping.api.entity.Account;
import com.animalchipping.api.entity.AnimalType;
import com.animalchipping.api.entity.Location;
import com.animalchipping.api.repository.AccountRepository;
import com.animalchipping.api.repository.AnimalTypeRepository;
import com.animalchipping.api.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    @Bean
    public CommandLineRunner commandLineRunner(AccountRepository accountRepository, LocationRepository locationRepository, AnimalTypeRepository animalTypeRepository) {
        return args -> {
            accountRepository.save(new Account("Алексей",
                    "Высоцкис",
                    "root@email.com",
                    "root"
            ));

            locationRepository.save(new Location(56.0153, 92.8932));
            locationRepository.save(new Location(55.5323, 89.1835));
            locationRepository.save(new Location(56.2168, 95.7198));
            locationRepository.save(new Location(55.7558, 37.6173));
            locationRepository.save(new Location(59.9343, 30.3351));
            locationRepository.save(new Location(43.1332, 131.9113));
            locationRepository.save(new Location(43.1332, 131.9113));
            locationRepository.save(new Location(69.3558, 88.1893));
            locationRepository.save(new Location(44.8936, 37.3158));

            animalTypeRepository.save(new AnimalType("dog"));
            animalTypeRepository.save(new AnimalType("cat"));
            animalTypeRepository.save(new AnimalType("parrot"));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
