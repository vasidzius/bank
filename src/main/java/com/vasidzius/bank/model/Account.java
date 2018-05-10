package com.vasidzius.bank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;

@Entity(name = "ACCOUNTS")
@Data
@EqualsAndHashCode(of = "id")
public class Account {

    public Account(double state){
        this.state = state;
    }

    public Account(){};

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ACCOUNTS_SEQ", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNTS_SEQ")
    private long id;

    @Column(name = "STATE")
    private double state;
}
