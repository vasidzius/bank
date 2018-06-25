package com.vasidzius.bank.generator;

import lombok.SneakyThrows;

import java.util.Random;

public class GeneratingAmount {

    private static Random random = new Random();

    @SneakyThrows
    static double getAmount(double valueInterval) {
        double randomDouble = random.nextDouble() * valueInterval;
        String truncateValue = String.format("%.2f", randomDouble);
        try {
            return Double.valueOf(truncateValue);
        } catch (NumberFormatException e) {
            return Double.valueOf(truncateValue.replace(",", "."));
        }
    }
}
