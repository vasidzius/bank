package com.vasidzius.bank.controller;

import com.vasidzius.bank.jdbcrepository.AccountJdbcRepository;
import com.vasidzius.bank.model.Account;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LocksHolder {

    private AccountJdbcRepository accountRepository;
    @Getter
    private Map<Long, Object> locks;

    public LocksHolder(AccountJdbcRepository accountRepository) {
        this.accountRepository = accountRepository;
        locks = initialize();
    }

    private Map<Long, Object> initialize() {
        return accountRepository.findAll().stream().collect(Collectors.toMap(Account::getId, t -> new Object()));
    }
}
