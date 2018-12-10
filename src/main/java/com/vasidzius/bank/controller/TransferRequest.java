package com.vasidzius.bank.controller;

import lombok.Data;

@Data
public class TransferRequest {

    private Long fromAccountId;
    private Long toAccountId;
    private double amount;

    public TransferRequest(Long fromAccountId, Long toAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }
}
