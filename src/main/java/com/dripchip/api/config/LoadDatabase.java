package com.dripchip.api.config;

import com.dripchip.api.model.Account;
import com.dripchip.api.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(new Account("John", "Doe", "john-doe@mail.com", "qwerty"));
            accountRepository.save(new Account("Jane", "Doe", "jane-doe@mail.com", "12345"));
            accountRepository.save(new Account("Sam", "Doe", "sam-doe@mail.com", "q1w2e3r4t5y6"));
        };
    }
}
