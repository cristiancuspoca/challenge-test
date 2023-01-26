package com.neoris.challenge.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionContext {

    private TransactionType transactionType;
    public void selectTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    public BigDecimal apply(BigDecimal balance, BigDecimal amount) {
        return transactionType.apply(balance, amount);
    }
}
