package com.vasidzius.bank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class Transfer {
    private long id;

    private Long fromAccountId;

    private Long toAccountId;

    private long amount;

    private Boolean executed;

    public void setAmountUsingDoubleView(double amount) {
        this.amount = (long)(amount * 100);
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getAmountDoubleView() {
        return ((double) amount) / 100;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transfer{");
        sb.append("id=").append(id);
        sb.append(", fromAccountId=").append(fromAccountId);
        sb.append(", toAccountId=").append(toAccountId);
        sb.append(", amount=").append((double)amount / 100);
        sb.append(", executed=").append(executed);
        sb.append('}');
        return sb.toString();
    }
}
