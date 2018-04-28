package com.vasidzius.bank.controller;

import com.vasidzius.bank.model.Transfer;

public interface TransfersExecutor {
    void startTransfers();

    void startTransfersForTesting();

    void passToExecution(Transfer transfer);

    void executeTransfer(Transfer transfer);

    boolean isTransfersPresented(long transferId);
}
