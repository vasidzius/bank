package com.vasidzius.bank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "TRANSFERS")
@Data
@EqualsAndHashCode(of = "id")
public class Transfer {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "TRANSFERS_SEQ", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSFERS_SEQ")
    private long id;

    @JoinColumn(name = "FROM_ACCOUNT_ID")
    @ManyToOne
    private Account fromAccountId;

    @JoinColumn(name = "TO_ACCOUNT_ID")
    @ManyToOne
    private Account toAccountId;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "EXECUTED")
    private Boolean executed;
}
