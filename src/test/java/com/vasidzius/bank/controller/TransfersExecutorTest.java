package com.vasidzius.bank.controller;

import com.vasidzius.bank.BankApplication;
import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BankApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TransfersExecutorTest {

    private static final int INITIAL_SEQUENCE_VALUE = 1000;

    @Autowired
    private AccountController accountController;

    @Autowired
    private TransferController transferController;

    @Autowired
    private TransfersExecutor transfersExecutor;

    private Random random = new Random();

    @Test
    @Transactional
    public void allTransfersShouldBeExecuted() throws InterruptedException {
        //given
        int accountsNumber = 100;
        double fullBank = 1000000; //1 million
        long transfersNumber = 1; //1 million
        createAccounts(accountsNumber, fullBank);
        fillTransfers(transfersNumber, accountsNumber, fullBank/accountsNumber * 0.01);

        //when
        transfersExecutor.startTransfers();

        //then
        Thread.sleep(3000);
        checkAccounts(accountsNumber, fullBank);

        checkTransfers(transfersNumber);

    }

    private void checkTransfers(long transfersNumber) {
        List<Transfer> all = transferController.findAll();
        assertEquals(transfersNumber + 4, all.size());
        long nullsOrFalseExecuteds = all.stream().map(Transfer::getExecuted).filter(executed -> executed == null || !executed).count();
        System.out.println(all.stream().map(Transfer::toString).collect(Collectors.joining("\n")));
        assertEquals(0, nullsOrFalseExecuteds);
    }

    private void checkAccounts(int accountsNumber, double fullBank) {
        List<Account> all = accountController.findAll();
        assertEquals(accountsNumber + 4, all.size());
        double sum = all.stream().mapToDouble(Account::getState).sum();
        assertEquals(fullBank + 400, sum, 1E-6);
    }

    private void fillTransfers(long transfersNumber, int accountsNumber, double valueInterval) {
        for(int i = 0; i < transfersNumber; i++){
            Transfer transfer = new Transfer();
            transfer.setAmount(random.nextDouble() * valueInterval);

            int fromAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            transfer.setFromAccount(accountController.find(fromAccountId));

            int toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            transfer.setToAccount(accountController.find(toAccountId));

            transferController.save(transfer);
        }
    }

    private void createAccounts(int accountsNumber, double fullBank) {
        double state = fullBank / accountsNumber;
        for(int i = 0; i < accountsNumber; i++){
            Account account = new Account(state);
            accountController.persist(account);
        }
    }

    @Test
    @Transactional
    public void executeTransfer(){
        //given
        Transfer transfer = transferController.find(1);

        //when
        transfersExecutor.executeTransfer(transfer);

        //then
        Account account1 = accountController.find(1);
        assertEquals(90, account1.getState(), 1E-6);
        Account account2 = accountController.find(2);
        assertEquals(110, account2.getState(), 1E-6);
    }
}