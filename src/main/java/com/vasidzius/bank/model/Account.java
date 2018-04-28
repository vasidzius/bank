package com.vasidzius.bank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "ACCOUNTS")
@Data
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ACCOUNTS_SEQ", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNTS_SEQ")
    private long id;

    @Column(name = "STATE")
    private double state;
}
