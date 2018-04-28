package com.vasidzius.bank.controller;

import lombok.SneakyThrows;
import org.junit.Test;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
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
