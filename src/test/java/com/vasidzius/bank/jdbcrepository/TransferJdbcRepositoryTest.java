package com.vasidzius.bank.jdbcrepository;

import com.vasidzius.bank.BankApplication;
import com.vasidzius.bank.BaseTest;
import com.vasidzius.bank.model.Transfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransferJdbcRepositoryTest extends BaseTest {

    @Autowired
    private TransferJdbcRepository transferJdbcRepository;

    @Test
    public void findAll() {
        //when
        List<Transfer> all = transferJdbcRepository.findAll();

        //then
        assertEquals(4, all.size());
    }

    @Test
    public void insert() {
        //given
        Transfer transfer = new Transfer();
        transfer.setFromAccountId(1L);
        transfer.setToAccountId(2L);
        transfer.setAmount(1000L);
        transfer.setExecuted(false);

        //when
        Transfer insertedTransfer = transferJdbcRepository.insert(transfer);

        //then
        assertTrue(insertedTransfer.getId() > 4);
        assertEquals(Long.valueOf(1), insertedTransfer.getFromAccountId());
        assertEquals(Long.valueOf(2), insertedTransfer.getToAccountId());
        assertEquals(1000L, insertedTransfer.getAmount(), 1E-6);
        assertEquals(false, insertedTransfer.getExecuted());
    }

    @Test
    public void find() {
        //when
        Transfer transfer = transferJdbcRepository.find(1);

        //then
        assertEquals(1, transfer.getId());
        assertEquals(Long.valueOf(1), transfer.getFromAccountId());
        assertEquals(Long.valueOf(2), transfer.getToAccountId());
        assertEquals(10, transfer.getAmount(), 1E-6);
        assertNull(transfer.getExecuted());
    }

    @Test
    public void update() {
        //given
        Transfer transferToBeUpdated = transferJdbcRepository.find(1);
        transferToBeUpdated.setAmount(25);
        transferToBeUpdated.setExecuted(false);

        //when
        transferJdbcRepository.update(transferToBeUpdated);
        Transfer updatedTransfer = transferJdbcRepository.find(1);

        //then
        assertEquals(1, updatedTransfer.getId());
        assertEquals(Long.valueOf(1), updatedTransfer.getFromAccountId());
        assertEquals(Long.valueOf(2), updatedTransfer.getToAccountId());
        assertEquals(10, updatedTransfer.getAmount(), 1E-6);
        assertFalse(updatedTransfer.getExecuted());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void notExist(){
        //when
        transferJdbcRepository.find(666);
    }
}