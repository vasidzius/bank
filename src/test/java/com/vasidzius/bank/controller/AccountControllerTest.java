package com.vasidzius.bank.controller;

import com.vasidzius.bank.BankApplication;
import com.vasidzius.bank.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BankApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AccountControllerTest {

    @Autowired
    private AccountController accountController;

    @Test
    public void findAll(){
        //when
        List<Account> all = accountController.findAll();

        //then
        assertEquals(4, all.size());
    }

    @Test
    @Transactional
    public void test() {
        Account account = accountController.find(1);
        account.setState(250);
//        accountController.persist(account);
        assertEquals(250, accountController.find(1).getState(), 1E-6);
        System.out.println(accountController.findAll());

    }

}