package com.vasidzius.bank.jdbcrepository;

import com.vasidzius.bank.BaseTest;
import com.vasidzius.bank.model.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountJdbcRepositoryTest extends BaseTest {

    @Autowired
    private AccountJdbcRepository accountJdbcRepository;

    @Test
    //@Transactional
    public void update(){
        //given
        Account accountForUpdate = new Account();
        accountForUpdate.setId(1);
        accountForUpdate.setBalance(20000);

        //when
        accountJdbcRepository.update(accountForUpdate);

        //then
        Account updatedAccount = accountJdbcRepository.find(1);
        assertEquals(20000, updatedAccount.getBalance());
    }

    @Test
    public void testFindAll(){
        //when
        List<Account> all = accountJdbcRepository.findAll();

        //then
        assertEquals(4, all.size());
    }

    @Test
    public void insert(){
        //given
        Account account = new Account(2500);

        //when
        Account insert = accountJdbcRepository.insert(account);

        //then
        Account accountInDb = accountJdbcRepository.find(insert.getId());
        assertEquals(2500, accountInDb.getBalance());
    }

    @Test
    public void find(){
        //when
        Account account = accountJdbcRepository.find(1);

        //then
        assertEquals(100, account.getBalance());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findNotExist(){
        //when
        Account account = accountJdbcRepository.find(666);

    }

    @Test
    public void delete() {
        //when
        accountJdbcRepository.delete(1);

        //then
        assertEquals(3, accountJdbcRepository.findAll().size());
        Account account = accountJdbcRepository.find(1);
        assertTrue(account.isDeleted());

    }
}