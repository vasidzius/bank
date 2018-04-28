package com.vasidzius.bank.controller;

import com.vasidzius.bank.BaseTest;
import com.vasidzius.bank.model.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.Assert.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerTest extends BaseTest {

    @Autowired
    private AccountController accountController;

    @Autowired
    private LocksHolder locksHolder;

    @Test
    public void findAll(){
        //when
        ResponseEntity<List<Account>> all = accountController.findAll();

        //then
        assertEquals(4, all.getBody().size());
    }

    @Test
    public void createAccount() {
        //when
        Account insertedAccount1 = accountController.createAccount(250);
        Account insertedAccount2 = accountController.createAccount(3.50);

        //then
        assertEquals(250, insertedAccount1.getBalanceDoubleView(), 1E-6);
        assertEquals(3.50, insertedAccount2.getBalanceDoubleView(), 1E-6);
        assertEquals(6, locksHolder.getLocks().size());
    }

    @Test
    public void deleteAccount() {
        //given
        Account insertedAccount = accountController.createAccount(250);

        //when
        accountController.deleteAccount(1);

        //then
        ResponseEntity<List<Account>> all = accountController.findAll();
        assertEquals(4, all.getBody().size());

    }

    @Test
    public void deleteAccountTwice() {
        //given
        ResponseEntity firstResponseEntity = accountController.deleteAccount(1);

        //when
        ResponseEntity secondResponseEntity = accountController.deleteAccount(1);

        //then
        assertEquals(HttpStatus.OK, firstResponseEntity.getStatusCode());
        assertEquals(HttpStatus.OK, secondResponseEntity.getStatusCode());
    }
}