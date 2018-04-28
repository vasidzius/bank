package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.vasidzius.bank.spring.Constants.INITIAL_SEQUENCE_VALUE;

@Component
@RequiredArgsConstructor
public class TransfersExecutorImpl implements TransfersExecutor {

    private final static Logger LOGGER = Logger.getLogger("Transfers");

    private final Executor taskExecutor;
    private final TransferController transferController;
    private final AccountController accountController;
    private final LocksHolder locksHolder;

    private TransfersExecutor transfersExecutor;

    @Autowired
    public void setTransfersExecutor(@Lazy TransfersExecutor transfersExecutor) {
        this.transfersExecutor = transfersExecutor;
    }

    @Override
    public void startTransfers() {
        LOGGER.log(Level.INFO, "Start transfers");
        long transferId = INITIAL_SEQUENCE_VALUE;
        while (true) {
            if (transfersExecutor.isTransfersPresented(transferId)) {
                Transfer transfer = transferController.find(transferId).getBody();
                transfersExecutor.passToExecution(transfer);
                transferId++;
            }
        }
    }

    @Override
    public void startTransfersForTesting() {
        long transferId = INITIAL_SEQUENCE_VALUE;
        while (transfersExecutor.isTransfersPresented(transferId)) {
            Transfer transfer = transferController.find(transferId).getBody();
            transfersExecutor.passToExecution(transfer);
            transferId++;
        }
    }

    @Override
    public void passToExecution(Transfer transfer) {
        taskExecutor.execute(() -> transfersExecutor.executeTransfer(transfer));
    }

    @Override
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void executeTransfer(Transfer transfer) {
        if (transfer.getToAccountId() == null && transfer.getFromAccountId() == null) {
            throw new IllegalArgumentException("Both accounts are null");
        } else if (transfer.getToAccountId() == null) {
            executeDecreasing(transfer);
        } else if (transfer.getFromAccountId() == null) {
            executeIncreasing(transfer);
        } else {
            executeTransferBetweenAccounts(transfer);
        }
    }

    private void executeIncreasing(Transfer transfer) {

        Long toAccountId = transfer.getToAccountId();
        synchronized (locksHolder.getLocks().get(toAccountId)) {
            processingIncreasing(transfer);
        }
    }

    private void executeDecreasing(Transfer transfer) {

        Long fromAccountId = transfer.getFromAccountId();
        synchronized (locksHolder.getLocks().get(fromAccountId)) {
            processingDecreasing(transfer);
        }
    }

    private void executeTransferBetweenAccounts(Transfer transfer) {
        long fromAccountId = transfer.getFromAccountId();
        long toAccountId = transfer.getToAccountId();

        Object first = locksHolder.getLocks().get(fromAccountId);
        Object second = locksHolder.getLocks().get(toAccountId);

        if (fromAccountId > toAccountId) {
            first = locksHolder.getLocks().get(toAccountId);
            second = locksHolder.getLocks().get(fromAccountId);
        }

        synchronized (first) {
            synchronized (second) {
                processingBetweenAccounts(transfer);
            }
        }
    }

    private void processingIncreasing(Transfer transfer) {
        LOGGER.log(Level.INFO, "increasing " + transfer);
        Account toAccount = accountController.find(transfer.getToAccountId()).getBody();
        long amount = transfer.getAmount();
        if (Objects.requireNonNull(toAccount).isDeleted()) {
            transfer.setExecuted(false);
            transferController.update(transfer);
        } else {
            toAccount.setBalance(toAccount.getBalance() + amount);
            transfer.setExecuted(true);
            accountController.update(toAccount);
            transferController.update(transfer);
        }
    }

    private void processingDecreasing(Transfer transfer) {
        LOGGER.log(Level.INFO, "decreasing " + transfer);
        Account fromAccount = accountController.find(transfer.getFromAccountId()).getBody();
        long amount = transfer.getAmount();
        if (Objects.requireNonNull(fromAccount).isDeleted() ||
                fromAccount.getBalance() < amount) {
            transfer.setExecuted(false);
            transferController.update(transfer);
        } else {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            transfer.setExecuted(true);
            accountController.update(fromAccount);
            transferController.update(transfer);
        }
    }

    private void processingBetweenAccounts(Transfer transfer) {
        LOGGER.log(Level.INFO, "between two " + transfer);

        Account fromAccount = accountController.find(transfer.getFromAccountId()).getBody();
        Account toAccount = accountController.find(transfer.getToAccountId()).getBody();

        long amount = transfer.getAmount();

        if (Objects.requireNonNull(fromAccount).isDeleted() ||
                Objects.requireNonNull(toAccount).isDeleted() ||
                fromAccount.getBalance() < amount) {
            transfer.setExecuted(false);
            transferController.update(transfer);

        } else {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            transfer.setExecuted(true);
            accountController.update(fromAccount);
            accountController.update(toAccount);
            transferController.update(transfer);
        }
        LOGGER.log(Level.INFO, "result " + transfer);
    }

    @Override
    public boolean isTransfersPresented(long transferId) {
        return transferController.find(transferId).getStatusCode() != HttpStatus.NO_CONTENT;
    }
}
