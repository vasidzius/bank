package com.vasidzius.bank.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@ApiModel
public class Account {

    public Account(long balance){
        this.balance = balance;
    }

    public Account(double balance){
        this.balance = (long)(balance * 100);
    }

    @ApiModelProperty(readOnly = true)
    private long id;
    @ApiModelProperty(value = "Internal representation of Balance, use Long type", required = true)
    private long balance;
    @ApiModelProperty(value = "Is Account deleted?")
    private boolean deleted;

    public Account() {
    }

    @ApiModelProperty(value = "External representation of Balance, use Double type", readOnly = true)
    public double getBalanceDoubleView() {
        return ((double)balance) / 100;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", balance=").append((double)balance / 100);
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
