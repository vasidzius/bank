package com.vasidzius.bank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "TRANSACTIONS")
@Data
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "TRANSACTIONS_SEQ", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTIONS_SEQ")
    private long id;

    @JoinColumn(name = "FROM_ACCOUNT_ID")
    @ManyToOne
    private Account fromAccountId;

    @JoinColumn(name = "TO_ACCOUNT_ID")
    @ManyToOne
    private Account toAccountId;

    @Column(name = "AMOUNT")
    private double amount;
}
