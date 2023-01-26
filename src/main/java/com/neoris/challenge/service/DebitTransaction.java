package com.neoris.challenge.service;

import com.neoris.challenge.exception.TransactionException;

import java.math.BigDecimal;

public class DebitTransaction implements TransactionType {

    @Override
    public BigDecimal apply(BigDecimal balance, BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        if (BigDecimal.ZERO.compareTo(newBalance) > 0) {
            throw new TransactionException(
                    String.format("Insufficient funds: balance=%s, debit amount=%s", balance, amount));
        }
        return newBalance;
    }
}
