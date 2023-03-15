package com.dripchip.api.controller;

import com.dripchip.api.entity.Account;
import com.dripchip.api.entity.dto.AccountDto;
import com.dripchip.api.entity.specification.AccountSpecifications;
import com.dripchip.api.repository.AccountRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) {
        if (accountId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Account account = accountOptional.get();
        AccountDto accountDto = buildAccountDtoFromAccount(account);

        return ResponseEntity.ok().body(accountDto);
    }

    @GetMapping("/accounts/search")
    public ResponseEntity<List<AccountDto>> searchAccounts(
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

        List<AccountDto> accountDtoList = accounts.stream()
                .map(AccountController::buildAccountDtoFromAccount)
                .toList();

        return ResponseEntity.ok().body(accountDtoList);
    }

    @PostMapping("/registration")
    public ResponseEntity<AccountDto> registerAccount(@Valid @RequestBody Account account) {
        Optional<Account> accountOptional = accountRepository.findByEmail(account.getEmail());

        if (accountOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        account = accountRepository.save(account);

        AccountDto accountDto = buildAccountDtoFromAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @PutMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(
            @PathVariable("accountId") Long accountId,
            @Valid @RequestBody Account accountFromRequest
    ) {
        if (accountId == null || accountId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Account accountToUpdate = accountOptional.get();

        if (!accountToUpdate.getEmail().equals(accountFromRequest.getEmail())) {
            accountOptional = accountRepository.findByEmail(accountFromRequest.getEmail());

            if (accountOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        accountToUpdate.setFirstName(accountFromRequest.getFirstName());
        accountToUpdate.setLastName(accountFromRequest.getLastName());
        accountToUpdate.setEmail(accountFromRequest.getEmail());
        accountToUpdate.setPassword(accountFromRequest.getPassword());

        Account updatedAccount = accountRepository.save(accountToUpdate);

        AccountDto accountDto = buildAccountDtoFromAccount(updatedAccount);

        return ResponseEntity.ok().body(accountDto);
    }

    private static AccountDto buildAccountDtoFromAccount(Account account) {
        return new AccountDto(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
        );
    }
}
