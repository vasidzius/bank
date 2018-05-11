package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor()
@RequestMapping(value = "/accounts")
@Transactional
public class AccountController  {

    private final AccountRepository accountRepository;

    @GetMapping
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @PostMapping
    public Account persist(@RequestParam Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @GetMapping(value = "/{accountId}")
    public Account find(@PathVariable("accountId") long accountId) {
        return accountRepository.getOne(accountId);
    }
}
