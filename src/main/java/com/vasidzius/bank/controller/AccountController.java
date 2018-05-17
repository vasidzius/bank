package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.repositories.jparepository.AccountJpaRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/accounts")
public class AccountController  {

    private final AccountJpaRepository accountRepository;
//    private final AccountJdbcRepository accountRepository;

    @GetMapping
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account persist(@RequestParam Account account) {
        return accountRepository.saveAndFlush(account);
    }

    @GetMapping(value = "/{accountId}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account find(@PathVariable("accountId") long accountId) {
        return accountRepository.getOne(accountId);
    }
}
