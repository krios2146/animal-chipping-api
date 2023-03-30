package com.animalchipping.api;

import com.animalchipping.api.entity.Account;
import com.animalchipping.api.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    @Bean
    public CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        return args -> accountRepository.save(
                new Account("RootFirst",
                        "RootLast",
                        "root@email.com",
                        "root"
                )
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
