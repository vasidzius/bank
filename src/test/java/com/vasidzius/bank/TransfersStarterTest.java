package com.vasidzius.bank;

import com.vasidzius.bank.controller.AccountController;
import com.vasidzius.bank.controller.OperationController;
import com.vasidzius.bank.controller.TransferController;
import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BankApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TransfersStarterTest {

    private static final int INITIAL_SEQUENCE_VALUE = 1000;

    @Autowired
    private OperationController operationController;

    @Autowired
    private AccountController accountController;

    @Autowired
    private TransferController transferController;

    @Autowired
    private TransfersStarter starter;

    private Random random = new Random();

    @Test
    public void mainTest() throws InterruptedException {
        //given
        int accountsNumber = 100;
        double fullBank = 1000000; //1 million
        long transfersNumber = 1000; //1 million
        createAccounts(accountsNumber, fullBank);
        fillTransfers(transfersNumber, accountsNumber, fullBank/accountsNumber);

        //when
        starter.startTransfers();

        //then
        Thread.sleep(3000);
        checkAccounts(accountsNumber, fullBank);

        List<Transfer> all = checkTransfers(transfersNumber);
        System.out.println(all.stream().map(Transfer::toString).collect(Collectors.joining("\n")));

    }

    private List<Transfer> checkTransfers(long transfersNumber) {
        List<Transfer> all = transferController.findAll();
        assertEquals(transfersNumber + 4, all.size());
        return all;
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
            transfer.setFromAccount(accountController.find(fromAccountId).orElseThrow(() -> new RuntimeException("Can't find account with id " + fromAccountId)));

            int toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            transfer.setToAccount(accountController.find(toAccountId).orElseThrow(() -> new RuntimeException("Can't find account with id " + toAccountId)));

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
}