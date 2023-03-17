package com.animalchipping.api.controller;

import com.animalchipping.api.entity.Account;
import com.animalchipping.api.entity.dto.AccountDto;
import com.animalchipping.api.repository.AccountRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class AuthenticationController {
    private final AccountRepository accountRepository;

    public AuthenticationController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping
    public ResponseEntity<AccountDto> registerAccount(@Valid @RequestBody Account account) {
        Optional<Account> accountOptional = accountRepository.findByEmail(account.getEmail());

        if (accountOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        account = accountRepository.save(account);

        AccountDto accountDto = getDtoFrom(account);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    // TODO: Method already exists in the `AccountController`
    private static AccountDto getDtoFrom(Account account) {
        return new AccountDto(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
        );
    }
}
