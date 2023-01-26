package com.neoris.challenge.service;

import java.math.BigDecimal;

public class CreditTransaction implements TransactionType {

    @Override
    public BigDecimal apply(BigDecimal balance, BigDecimal amount) {
        return balance.add(amount);
    }
}
