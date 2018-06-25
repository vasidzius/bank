package com.vasidzius.bank.controller;

import com.vasidzius.bank.BaseTest;
import com.vasidzius.bank.generator.ValueGenerator;
import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.vasidzius.bank.spring.Constants.INITIAL_SEQUENCE_VALUE;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransfersExecutorTest extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger("Test");

    @Autowired
    private AccountController accountController;

    @Autowired
    private TransferController transferController;

    @Autowired
    private TransfersExecutor transfersExecutor;

    @Autowired
    private Executor taskExecutor;

    @Autowired
    private ValueGenerator valueGenerator;

    private Random random = new Random();

    @Test
    @Ignore
    public void BFT() {
        //given
        runFT(1234567,
                100,
                100,
                1000,
                5000,
                5000,
                30000);
    }

    @Test
//    @Ignore
    public void sFT() {
        //given
        runFT(123456,
                100,
                100,
                1000,
                5000,
                5000,
                15000);
    }

    private void runFT(int accountsNumber, int threadNumberBetweenTwo, int transfersBetweenTwo, int accountsToDelete, int transfersIncreasing, int transfersDecreasing, int delayBeforeStartTransfersMillis) {

        double fullBank = valueGenerator.generateValues(accountsNumber, threadNumberBetweenTwo, transfersBetweenTwo, accountsToDelete, transfersIncreasing, transfersDecreasing);

        startTransfers(delayBeforeStartTransfersMillis);

        //then
        long transfersNumber = threadNumberBetweenTwo * transfersBetweenTwo
                + transfersIncreasing
                + transfersDecreasing;
        checkTransfers(transfersNumber);
        double increasingSum = transferController.findAll().getBody().stream()
                .filter(transfer -> transfer.getFromAccountId() == null && transfer.getExecuted())
                .mapToDouble(Transfer::getAmountDoubleView)
                .sum();
        double decreasingSum = transferController.findAll().getBody().stream()
                .filter(transfer -> transfer.getToAccountId() == null && transfer.getExecuted())
                .mapToDouble(Transfer::getAmountDoubleView)
                .sum();
        checkAccounts(accountsNumber, fullBank + increasingSum - decreasingSum);
    }

    @Test
//    @Ignore
    public void allTransfersBetweenTwoAccountsShouldBeExecuted() {
        //given
        LOGGER.info("Generate values");
        int accountsNumber = 1234501;
        double initialBalance = 123456.58;
        long transfersNumber = 45671;
        valueGenerator.createAccounts(accountsNumber, initialBalance);
        valueGenerator.randomFillTransfers(transfersNumber, accountsNumber, initialBalance * 0.01);
        double fullBank = accountController.findAll().getBody().stream().mapToDouble(Account::getBalanceDoubleView).sum();

        //when
        startTransfers(0);

        //then
        checkTransfers(transfersNumber);
        checkAccounts(accountsNumber, fullBank);
    }

    @Test
    public void allTransfersBetweenTwoAccountsShouldBeExecutedSmall() {
        //given
        LOGGER.info("Generate values");
        int accountsNumber = 1000;
        double initialBalance = 10000;
        long transfersNumber = 1234;
        valueGenerator.createAccounts(accountsNumber, initialBalance);
        valueGenerator.randomFillTransfers(transfersNumber, accountsNumber, initialBalance * 0.01);
        double fullBank = accountController.findAll().getBody().stream().mapToDouble(Account::getBalanceDoubleView).sum();

        //when
        startTransfers(0);

        //then
        checkTransfers(transfersNumber);
        checkAccounts(accountsNumber, fullBank);
    }

    @Test
    public void allTransfersShouldBeExecutedSmall() {
        //given
        LOGGER.info("Generate values");
        int accountsNumber = 1000;
        double initialBalance = 10000;
        long transfersNumber = 1234;
        long increasingTransfersNumber = 50;
        long decreasingTransfersNumber = 100;
        long transfersBetweenTwo = transfersNumber - increasingTransfersNumber - decreasingTransfersNumber;
        valueGenerator.createAccounts(accountsNumber, initialBalance);
        valueGenerator.randomFillTransfers(transfersBetweenTwo, accountsNumber, initialBalance * 0.01);
        valueGenerator.createTransfersToIncreasing(increasingTransfersNumber, accountsNumber, initialBalance * 0.01);
        valueGenerator.createTransfersToDecreasing(decreasingTransfersNumber, accountsNumber, initialBalance * 0.01);
        double fullBank = accountController.findAll().getBody().stream().mapToDouble(Account::getBalanceDoubleView).sum();

        //when
        startTransfers(0);

        //then
        checkTransfers(transfersNumber);
        double increasingSum = transferController.findAll().getBody().stream().filter(transfer -> transfer.getFromAccountId() == null).mapToDouble(Transfer::getAmountDoubleView).sum();
        double decreasingSum = transferController.findAll().getBody().stream().filter(transfer -> transfer.getToAccountId() == null).mapToDouble(Transfer::getAmountDoubleView).sum();
        checkAccounts(accountsNumber, fullBank + increasingSum - decreasingSum);
    }

    @Test
    public void allTransfersBetweenTwoAccountsShouldBeExecutedSmallExceptDeletedAccounts() {
        //given
        LOGGER.info("Generate values");
        int accountsNumber = 100;
        double initialBalance = 10000;
        long transfersNumber = 100;
        valueGenerator.createAccounts(accountsNumber, initialBalance);
        valueGenerator.randomFillTransfers(transfersNumber, accountsNumber, initialBalance * 0.01);
        int numberToDelete = 10;
        double fullBank = accountController.findAll().getBody().stream().mapToDouble(Account::getBalanceDoubleView).sum();
        List<Long> deletedAccounts = randomDeleteAccounts(numberToDelete, accountsNumber);

        //when
        startTransfers(0);

        //then
        checkTransfersForDeletingAccountsTest(transfersNumber, deletedAccounts);
        checkAccounts(accountsNumber, fullBank);
    }

    private void checkTransfersForDeletingAccountsTest(long transfersNumber, List<Long> deletedAccounts) {
        List<Transfer> allTransfers = transferController.findAll().getBody();
        assertEquals(transfersNumber + 4, allTransfers.size());
        long nullsExecuteds = allTransfers.stream().map(Transfer::getExecuted).filter(Objects::isNull).count();
        assertEquals(4, nullsExecuteds);
        List<Transfer> falseExecuteds = allTransfers.stream().filter(transfer -> transfer.getExecuted() != null && !transfer.getExecuted()).collect(Collectors.toList());
        falseExecuteds.forEach(transfer -> assertTrue(deletedAccounts.contains(transfer.getFromAccountId()) || deletedAccounts.contains(transfer.getToAccountId())));
    }

    private List<Long> randomDeleteAccounts(int numberToDelete, int accountsNumber) {
        List<Long> deletedAccounts = new ArrayList<>();
        for (int i = 0; i < numberToDelete; i++) {
            long deletedAccountId = getBoundedRandom(accountsNumber, deletedAccounts);
            accountController.deleteAccount(deletedAccountId);
            deletedAccounts.add(deletedAccountId);
        }
        return deletedAccounts;
    }

    private int getBoundedRandom(int accountsNumber, List<Long> deletedAccounts) {
        int random = this.random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        while (deletedAccounts.contains((long) random)) {
            random = this.random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        }
        return random;
    }

    private void startTransfers(int delayBeforeStartMillis) {
        LOGGER.info("Start transfers");
        long start = System.nanoTime();
        waitingFewSecsForFillingtransfers(delayBeforeStartMillis);
        transfersExecutor.startTransfersForTesting();
        waitUntilAllTransfersAreExecuted();
        long elapsedTime = System.nanoTime() - start;
        System.out.println(String.format("With %s it takes %d millisec", taskExecutor.getClass().getSimpleName(), TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS)));
    }

    private void waitingFewSecsForFillingtransfers(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transfersBetweenTwoAccounts() {
        //given
        int accountsNumber = 2;
        long initialBalance = 5000;
        long transfersNumber = 5000;
        valueGenerator.createAccounts(accountsNumber, initialBalance);
        int fromAccountId = INITIAL_SEQUENCE_VALUE;
        int toAccountId = INITIAL_SEQUENCE_VALUE + 1;
        fillTransfers(transfersNumber / 2, fromAccountId, toAccountId);
        fillTransfers(transfersNumber / 2, toAccountId, fromAccountId);

        //when
        transfersExecutor.startTransfersForTesting();

        //then
        waitUntilAllTransfersAreExecuted();
        Account accountFrom = accountController.find(fromAccountId).getBody();
        Account accountTo = accountController.find(toAccountId).getBody();
        assertEquals(initialBalance, accountFrom.getBalanceDoubleView(), 1E-6);
        assertEquals(initialBalance, accountTo.getBalanceDoubleView(), 1E-6);
        checkAccounts(accountsNumber, initialBalance * 2 + 4);
        checkTransfers(transfersNumber);
    }

    @Test
    public void allMoneyFromOneToAnother() {
        //given
        int accountsNumber = 2;
        long initialBalance = 5000;
        long transfersNumber = 5000;
        valueGenerator.createAccounts(accountsNumber, initialBalance);
        int fromAccountId = INITIAL_SEQUENCE_VALUE;
        int toAccountId = INITIAL_SEQUENCE_VALUE + 1;
        fillTransfers(transfersNumber, fromAccountId, toAccountId);

        //when
        transfersExecutor.startTransfersForTesting();

        //then
        waitUntilAllTransfersAreExecuted();
        Account accountFrom = accountController.find(fromAccountId).getBody();
        Account accountTo = accountController.find(toAccountId).getBody();
        assertEquals(0, accountFrom.getBalanceDoubleView(), 1E-6);
        assertEquals(initialBalance * 2, accountTo.getBalanceDoubleView(), 1E-6);
        checkAccounts(accountsNumber, initialBalance * 2 + 4);
        checkTransfers(transfersNumber);
    }

    private <T> void printCollection(List<T> all) {
        LOGGER.info(all.stream().map(T::toString).collect(Collectors.joining("\n")));
    }

    private void waitUntilAllTransfersAreExecuted() {
        boolean flag = true;
        while (flag) {
            long count = transferController.findAll().getBody().stream().filter(transfer -> transfer.getId() >= INITIAL_SEQUENCE_VALUE && transfer.getExecuted() == null).count();
            if (count == 0) {
                flag = false;
            }
        }
    }

    private void checkTransfers(long transfersNumber) {
        List<Transfer> all = transferController.findAll().getBody();
        assertEquals(transfersNumber + 4, all.size());
        long nullsExecuteds = all.stream().map(Transfer::getExecuted).filter(Objects::isNull).count();
        assertEquals(4, nullsExecuteds);
    }

    private void checkAccounts(int accountsNumber, double fullBank) {
        List<Account> all = accountController.findAll().getBody();
        List<Account> allDeleted = accountController.findAllDeleted().getBody();
        assertEquals(accountsNumber + 4, all.size() + allDeleted.size());
        double sum = all.stream().mapToDouble(Account::getBalanceDoubleView).sum();
        double sumDeleted = allDeleted.stream().mapToDouble(Account::getBalanceDoubleView).sum();
        assertEquals(fullBank, sum + sumDeleted, 1E-2);
    }

    private void fillTransfers(long transfersNumber, long fromAccountId, long toAccountId) {
        for (int i = 0; i < transfersNumber; i++) {
            TransferRequest transferRequest = new TransferRequest(fromAccountId, toAccountId, 1);
            transferController.create(transferRequest);
        }
    }

    @Test
    //@Transactional
    public void executeTransfer() {
        //given
        Transfer transfer = transferController.find(1).getBody();

        //when
        transfersExecutor.executeTransfer(transfer);

        //then
        Account account1 = accountController.find(1).getBody();
        assertEquals(0.9, account1.getBalanceDoubleView(), 1E-6);
        Account account2 = accountController.find(2).getBody();
        assertEquals(1.1, account2.getBalanceDoubleView(), 1E-6);
    }

    @Test
    public void executeTransferFromDeletedAccount() {
        //given
        accountController.deleteAccount(1);
        Transfer transfer = transferController.find(1).getBody();

        //when
        transfersExecutor.executeTransfer(transfer);

        //then
        Transfer processedTransfer = transferController.find(1).getBody();
        assertFalse(processedTransfer.getExecuted());
        Account account1 = accountController.find(1).getBody();
        assertEquals(1, account1.getBalanceDoubleView(), 1E-6);
        Account account2 = accountController.find(2).getBody();
        assertEquals(1, account2.getBalanceDoubleView(), 1E-6);
    }

    @Test
    public void executeIncreasingTransfer() {
        //given
        Account account = accountController.find(1).getBody();
        double initialBalance = account.getBalanceDoubleView();
        double amount = 0.46;
        TransferRequest transferRequest = new TransferRequest(null, 1L, amount);
        transferController.create(transferRequest);
        Transfer transfer = transferController.find(INITIAL_SEQUENCE_VALUE).getBody();

        //when
        transfersExecutor.executeTransfer(transfer);

        //then
        Transfer executedTransfer = transferController.find(INITIAL_SEQUENCE_VALUE).getBody();
        assertTrue(executedTransfer.getExecuted());
        Account increasedAccount = accountController.find(1).getBody();
        assertEquals(increasedAccount.getBalanceDoubleView(), initialBalance + amount, 1E-6);
    }

    @Test
    public void executeSuccessDecreasingTransfer() {
        //given
        Account account = accountController.find(1).getBody();
        double initialBalance = account.getBalanceDoubleView();
        double amount = 0.46;
        TransferRequest transferRequest = new TransferRequest(1L, null, amount);
        transferController.create(transferRequest);
        Transfer transfer = transferController.find(INITIAL_SEQUENCE_VALUE).getBody();

        //when
        transfersExecutor.executeTransfer(transfer);

        //then
        Transfer executedTransfer = transferController.find(INITIAL_SEQUENCE_VALUE).getBody();
        assertTrue(executedTransfer.getExecuted());
        Account decreasedAccount = accountController.find(1).getBody();
        assertEquals(decreasedAccount.getBalanceDoubleView(), initialBalance - amount, 1E-6);
    }

    @Test
    public void executeFailedDecreasingTransfer() {
        //given
        Account account = accountController.find(1).getBody();
        double initialBalance = account.getBalanceDoubleView();
        double amount = 1.46;
        TransferRequest transferRequest = new TransferRequest(1L, null, amount);
        transferController.create(transferRequest);
        Transfer transfer = transferController.find(INITIAL_SEQUENCE_VALUE).getBody();

        //when
        transfersExecutor.executeTransfer(transfer);

        //then
        Transfer executedTransfer = transferController.find(INITIAL_SEQUENCE_VALUE).getBody();
        assertFalse(executedTransfer.getExecuted());
        Account decreasedAccount = accountController.find(1).getBody();
        assertEquals(decreasedAccount.getBalanceDoubleView(), initialBalance, 1E-6);
    }
}