package com.vasidzius.bank.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransferTest {

    @Test
    public void testToString() {
        Transfer transfer = new Transfer();
        transfer.setFromAccountId(1L);
        transfer.setToAccountId(2L);
        transfer.setAmount(2534L);
        transfer.setExecuted(false);
        assertEquals("Transfer{id=0, fromAccountId=1, toAccountId=2, amount=25.34, executed=false}", transfer.toString());
    }
}