package com.animalchipping.api.controller;

import com.animalchipping.api.entity.Account;
import com.animalchipping.api.entity.dto.AccountDto;
import com.animalchipping.api.entity.specification.AccountSpecifications;
import com.animalchipping.api.repository.AccountRepository;
import com.animalchipping.api.repository.AnimalRepository;
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
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {
    private final AccountRepository accountRepository;
    private final AnimalRepository animalRepository;

    public AccountController(AccountRepository accountRepository,
                             AnimalRepository animalRepository) {
        this.accountRepository = accountRepository;
        this.animalRepository = animalRepository;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long accountId) {
        if (accountId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Account account = accountOptional.get();
        AccountDto accountDto = getDtoFrom(account);

        return ResponseEntity.ok().body(accountDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> searchAccounts(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size) {

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
                .map(AccountController::getDtoFrom)
                .toList();

        return ResponseEntity.ok().body(accountDtoList);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("accountId") Long accountId,
                                                    @Valid @RequestBody Account accountFromRequest) {

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

        AccountDto accountDto = getDtoFrom(updatedAccount);

        return ResponseEntity.ok().body(accountDto);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId) {
        if (accountId == null || accountId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Account account = accountOptional.get();

        if (animalRepository.existsByChipper(account)) {
            return ResponseEntity.badRequest().build();
        }

        accountRepository.delete(account);

        return ResponseEntity.ok().build();
    }

    private static AccountDto getDtoFrom(Account account) {
        return new AccountDto(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
        );
    }
}
