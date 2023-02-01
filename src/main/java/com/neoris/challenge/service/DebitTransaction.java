package com.neoris.challenge.service;

import com.neoris.challenge.exception.TransactionException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class DebitTransaction implements TransactionType {

    @Override
    public BigDecimal apply(BigDecimal balance, BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        if (BigDecimal.ZERO.compareTo(newBalance) > 0) {
            log.error("Insufficient funds: balance={}, debit amount={}", balance, amount);
            throw new TransactionException(
                    String.format("Insufficient funds: balance=%s, debit amount=%s", balance, amount));
        }
        return newBalance;
    }
}
