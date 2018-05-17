package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import com.vasidzius.bank.spring.ApplicationContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
public class TransfersExecutorImpl implements TransfersExecutor {

//    private static final Logger LOGGER = Logger.getLogger(TransfersExecutorImpl.class.getName());
    private final static long INITIAL_TRANSFER_ID = 1000;

    private final ExecutorService executorService;
    private final TaskExecutor taskExecutor;
    private final TransferController transferController;
    private final AccountController accountController;
    private final LocksHolder locksHolder;

    @Autowired
    public void setTransfersExecutor(@Lazy TransfersExecutor transfersExecutor) {
        this.transfersExecutor = transfersExecutor;
    }

    private TransfersExecutor transfersExecutor;

    @Override
    public void startTransfers() {

        taskExecutor.execute(() -> {
            long transferId = INITIAL_TRANSFER_ID;
            while (true) {
                if (transfersExecutor.isTransfersPresented(transferId)) {
                    Transfer transfer = transferController.find(transferId);
                    transfersExecutor.passToExecution(transfer);
                    transferId++;
                }
            }
        });
    }

    @Override
    public void passToExecution(Transfer transfer) {
        executorService.execute(() -> {
            transfersExecutor.executeTransfer(transfer);
        });
    }


    @Override
    public void executeTransfer(Transfer transfer) {
        long fromAccountId = transfer.getFromAccount().getId();
        long toAccountId = transfer.getToAccount().getId();
        Object first;
        Object second;
        if(fromAccountId < toAccountId) {
            first = locksHolder.getLocks().get(fromAccountId);
            second = locksHolder.getLocks().get(toAccountId);
        } else {
            first = locksHolder.getLocks().get(toAccountId);
            second = locksHolder.getLocks().get(fromAccountId);
        }
        synchronized (first) {
            synchronized (second) {
                Account fromAccount = transfer.getFromAccount();
                Account toAccount = transfer.getToAccount();
                double amount = transfer.getAmount();

                if (fromAccount.getState() < amount) {
                    transfer.setExecuted(false);
                } else {
                    fromAccount.setState(fromAccount.getState() - amount);
                    toAccount.setState(toAccount.getState() + amount);
                    transfer.setExecuted(true);
                }
            }
        }
    }

    @Override
    public boolean isTransfersPresented(long transferId) {
        try {
            transferController.find(transferId);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
