package com.vasidzius.bank.controller;

import com.vasidzius.bank.BaseTest;
import com.vasidzius.bank.model.Transfer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransferControllerTest extends BaseTest {

    @Autowired
    private TransferController transferController;

    @Test
    public void notExist(){
        //when
        Transfer transfer = transferController.find(666).getBody();

        //then
        assertNull(transfer);
    }

    @Test
    public void create(){
        //when
        double amount = 34.56;
        TransferRequest transferRequest = new TransferRequest(1L, 2L, amount);
        Transfer transfer = transferController.create(transferRequest);

        //then
        Transfer transfer1000 = transferController.find(1000).getBody();
        assertEquals(amount, transfer1000.getAmountDoubleView(), 1E-6);
    }

    @Test
    public void addIncreasingTransfer() {
        //given
        double amount = 34.56;

        //when
        TransferRequest transferRequest = new TransferRequest(null, 1L, amount);
        Transfer transfer = transferController.create(transferRequest);

        //then
        Transfer transfer1000 = transferController.find(1000).getBody();
        assertEquals(amount, transfer1000.getAmountDoubleView(), 1E-6);
        assertNull(transfer1000.getFromAccountId());
        assertEquals(Long.valueOf(1), transfer1000.getToAccountId());
    }

    @Test
    public void addDecreasingTransfer() {
        //given
        double amount = 34.56;

        //when
        TransferRequest transferRequest = new TransferRequest(1L, null, amount);
        Transfer transfer = transferController.create(transferRequest);

        //then
        Transfer transfer1000 = transferController.find(1000).getBody();
        assertEquals(amount, transfer1000.getAmountDoubleView(), 1E-6);
        assertNull(transfer1000.getToAccountId());
        assertEquals(Long.valueOf(1), transfer1000.getFromAccountId());
    }

}