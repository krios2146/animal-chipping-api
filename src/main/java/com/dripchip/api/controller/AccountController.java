package com.dripchip.api.controller;

import com.dripchip.api.entity.Account;
import com.dripchip.api.entity.specification.AccountSpecifications;
import com.dripchip.api.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        if (accountId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(account.get());
    }

    @GetMapping("/accounts/search")
    public ResponseEntity<List<Account>> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {

        if (size <= 0 || from < 0) {
            return ResponseEntity.badRequest().build();
        }

        Specification<Account> spec = Specification.where(AccountSpecifications.firstNameContainsIgnoreCase(firstName)
                .and(AccountSpecifications.lastNameContainsIgnoreCase(lastName))
                .and(AccountSpecifications.emailContainsIgnoreCase(email))
        );

        Page<Account> accounts = accountRepository.findAll(spec, PageRequest.of(from, size));

        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(accounts.getContent());
    }

}
