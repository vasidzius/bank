package com.vasidzius.bank.jdbcrepository;

import com.vasidzius.bank.BankApplication;
import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.repositories.jdbcrepository.AccountJdbcRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BankApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class AccountJdbcRepositoryTest {

    @Autowired
    private AccountJdbcRepository accountJdbcRepository;

    @Test
    public void test(){
        List<Account> all = accountJdbcRepository.findAll();
        assertEquals(4, all.size());
    }

}