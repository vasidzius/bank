package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LocksHolder {

    private AccountController accountController;
    @Getter
    private Map<Long, Object> locks;

    public LocksHolder(AccountController accountController) {
        this.accountController = accountController;
        locks = initialize();
    }

    private Map<Long, Object> initialize() {
        return accountController.findAll().stream().collect(Collectors.toMap(Account::getId, t -> new Object()));
    }
}
