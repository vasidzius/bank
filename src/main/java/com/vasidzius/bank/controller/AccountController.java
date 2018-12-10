package com.vasidzius.bank.controller;

import com.vasidzius.bank.jdbcrepository.AccountJdbcRepository;
import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.LocksHolder;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/accounts", produces = "application/json")
@Api
@CrossOrigin(value = "http://localhost:4200")
public class AccountController {

    private final AccountJdbcRepository accountRepository;
    private final LocksHolder locksHolder;

    @ApiOperation(value = "view a list of all existing accounts except deleted")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There are no accounts")
    })
    @GetMapping
    public ResponseEntity<List<Account>> findAll() {
        List<Account> all = accountRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(all);
        }
        return ResponseEntity.ok(all);
    }

    @ApiOperation(value = "create Account by setting up balance in double form like ##.##")
    @PostMapping
    public ResponseEntity<Account> createAccount(@ApiParam(value = "only two digits after dot is allowed", required = true) @RequestBody double balance) {
        Account account = new Account(balance);
        Account insertedAccount = accountRepository.insert(account);
        locksHolder.getLocks().put(insertedAccount.getId(), new Object());
        return ResponseEntity.ok(insertedAccount);
    }

    @ApiOperation(value = "Find Account by Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There is no account with given Id")
    })
    @GetMapping(value = "/{accountId}")
    public ResponseEntity<Account> find(@PathVariable("accountId") long accountId) {
        try {
            return ResponseEntity.ok(accountRepository.find(accountId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Delete account by Id. In fact this operation doesn't delete record in DB but change flag 'deleted' to TRUE")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There is no account with current Id")
    })
    @DeleteMapping(value = "/{accountId}")
    public ResponseEntity deleteAccount(@PathVariable("accountId") long accountId) {

        Object accountLock = locksHolder.getLocks().get(accountId);
        if (accountLock == null) {
            return ResponseEntity.noContent().build();
        }
        synchronized (locksHolder.getLocks().get(accountId)) {
            accountRepository.delete(accountId);
            return ResponseEntity.ok().build();
        }
    }

    void update(Account account) {
        accountRepository.update(account);
    }

    @ApiOperation(value = "Show all deleted Accounts")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There are no deleted accounts")
    })
    @GetMapping("/allDeleted")
    public ResponseEntity<List<Account>> findAllDeleted() {
        List<Account> allDeleted = accountRepository.findAllDeleted();
        if (allDeleted.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(allDeleted);
        }
        return ResponseEntity.ok(allDeleted);
    }

    @ApiOperation(value = "Show all Accounts include deleted")
    @ApiResponses({
            @ApiResponse(code = 204, message = "There are no accounts")
    })
    @GetMapping("/allIncludeDeleted")
    public ResponseEntity<List<Account>> findAllIncludeDeleted() {
        List<Account> allIncludeDeleted = accountRepository.findAllIncludeDeleted();
        if (allIncludeDeleted.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(allIncludeDeleted);
        }
        return ResponseEntity.ok(allIncludeDeleted);
    }
}
