package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Account;
import com.vasidzius.bank.model.Transfer;
import com.vasidzius.bank.spring.ApplicationContextProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
@Transactional
public class TransfersExecutor {

    private static final Logger LOGGER = Logger.getLogger(TransfersExecutor.class.getName());
    private final static long INITIAL_TRANSFER_ID = 1000;

    private ExecutorService executorService;
    private TransferController transferController;
    private AccountController accountController;
    private LocksHolder locksHolder;

    public void startTransfers() {

        executorService.execute(() -> {
            long transferId = INITIAL_TRANSFER_ID;
            while (true) {
                if (ApplicationContextProvider.getBean(TransfersExecutor.class).isTransfersPresented(transferId)) {
                    Transfer transfer = transferController.find(transferId);
                    ApplicationContextProvider.getBean(TransfersExecutor.class).passToExecution(transfer);
                    transferId++;
                }
            }
        });
    }

    public void passToExecution(Transfer transfer) {
        executorService.execute(() -> {
            ApplicationContextProvider.getBean(TransfersExecutor.class).executeTransfer(transfer);
        });
    }


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

    private boolean isTransfersPresented(long transferId) {
        try {
            transferController.find(transferId);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
