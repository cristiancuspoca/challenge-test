package com.neoris.challenge.service;

import java.math.BigDecimal;

public interface TransactionType {
    BigDecimal apply(BigDecimal balance, BigDecimal amount);
}
