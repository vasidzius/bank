package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/accounts")
@Transactional
public class AccountController  {

    private AccountRepository accountRepository;

    @GetMapping
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @PostMapping
    public Account persist(@RequestParam Account account) {
        return accountRepository.save(account);
    }

    @GetMapping(value = "/{accountId}")
    public Optional<Account> find(@PathVariable("accountId") long accountId) {
        return accountRepository.findById(accountId);
    }
}
