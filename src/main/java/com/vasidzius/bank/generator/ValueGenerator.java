package com.vasidzius.bank.generator;

import com.vasidzius.bank.controller.AccountController;
import com.vasidzius.bank.controller.TransferController;
import com.vasidzius.bank.controller.TransferRequest;
import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

import static com.vasidzius.bank.generator.GeneratingAmount.getAmount;
import static com.vasidzius.bank.spring.Constants.INITIAL_SEQUENCE_VALUE;

@Component
@RequiredArgsConstructor
public class ValueGenerator {

    private final AccountController accountController;
    private final Executor taskExecutor;
    private final TransferController transferController;

    private Random random = new Random();

    private static final Logger LOGGER = Logger.getLogger("ValueGenerator");

    public void createAccounts(int accountsNumber, double balance) {
        for (int i = 0; i < accountsNumber; i++) {
            accountController.createAccount(balance);
        }
    }

    public void randomFillTransfers(long transfersNumber, int accountsNumber, double valueInterval) {
        for (int i = 0; i < transfersNumber; i++) {
            long fromAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            long toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            if (fromAccountId != toAccountId) {
                TransferRequest transferRequest = new TransferRequest(fromAccountId, toAccountId, getAmount(valueInterval));
                transferController.create(transferRequest);
            } else {
                i--;
            }
        }
    }

    public void createTransferBetweenTwo(int accountsNumber, double valueInterval) {
        long fromAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        long toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        while (fromAccountId == toAccountId) {
            fromAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        }
        TransferRequest transferRequest = new TransferRequest(fromAccountId, toAccountId, getAmount(valueInterval));
        Transfer transfer = transferController.create(transferRequest);
        LOGGER.info("create between two" + transfer);
    }

    public double generateValues(
            int accountsNumber,
            int threadNumberBetweenTwo,
            int transfersBetweenTwo,
            int accountsToDelete,
            int transfersIncreasing,
            int transfersDecreasing) {
        LOGGER.info(String.format("Generate values: \n" +
                "accountsNumber %d, \n" +
                "threadNumberBetweenTwo %d, \n" +
                "transfersBetweenTwo %d, \n" +
                "accountsToDelete %d, \n" +
                "transfersIncreasing %d, \n" +
                "transfersDecreasing %d",
                accountsNumber,
                transfersBetweenTwo,
                transfersBetweenTwo,
                accountsToDelete,
                transfersIncreasing,
                transfersDecreasing));
        double initialBalance = 123456.58;
        double valueRangeForTransfersAmount = initialBalance * 0.01;
        createAccounts(accountsNumber, initialBalance);
        double fullBank = accountController.findAll().getBody().stream().mapToDouble(Account::getBalanceDoubleView).sum();

        //when
        for (int i = 0; i < threadNumberBetweenTwo; i++) {
            taskExecutor.execute(() -> {
                for (int j = 0; j < transfersBetweenTwo; j++) {
                    createTransferBetweenTwo(accountsNumber, valueRangeForTransfersAmount);
                }
            });
        }

        taskExecutor.execute(() -> {
            for (int i = 0; i < accountsToDelete; i++) {
                customWait();
                int deleteId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
                accountController.deleteAccount(deleteId);
                LOGGER.info("delete accountId" + deleteId);
            }
        });

        taskExecutor.execute(() -> {
            for (int i = 0; i < transfersIncreasing; i++) {
                createIncreasingTransfer(accountsNumber, valueRangeForTransfersAmount);
            }
        });

        taskExecutor.execute(() -> {
            for (int i = 0; i < transfersDecreasing; i++) {
                createDecreasingTransfer(accountsNumber, valueRangeForTransfersAmount);
            }
        });
        return fullBank;
    }

    public void createIncreasingTransfer(int accountsNumber, double valueInterval) {
        long toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        TransferRequest transferRequest = new TransferRequest(null, toAccountId, getAmount(valueInterval));
        Transfer transfer = transferController.create(transferRequest);
        LOGGER.info("create increasing" + transfer);
    }

    private void createDecreasingTransfer(int accountsNumber, double valueInterval) {
        long fromAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
        TransferRequest transferRequest = new TransferRequest(fromAccountId, null, getAmount(valueInterval));
        Transfer transfer = transferController.create(transferRequest);
        LOGGER.info("create decreasing" + transfer);
    }

    public void createTransfersToIncreasing(long increasingTransfersNumber, int accountsNumber, double valueInterval) {
        for (int i = 0; i < increasingTransfersNumber; i++) {
            long toAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            TransferRequest transferRequest = new TransferRequest(null, toAccountId, getAmount(valueInterval));
            transferController.create(transferRequest);
        }
    }

    public void createTransfersToDecreasing(long decreasingTransfersNumber, int accountsNumber, double valueInterval) {
        for (int i = 0; i < decreasingTransfersNumber; i++) {
            long fromAccountId = random.nextInt(accountsNumber) + INITIAL_SEQUENCE_VALUE;
            TransferRequest transferRequest = new TransferRequest(fromAccountId, null, getAmount(valueInterval));
            transferController.create(transferRequest);
        }
    }

    private void customWait() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
