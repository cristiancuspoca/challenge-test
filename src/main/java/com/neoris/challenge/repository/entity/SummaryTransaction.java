package com.neoris.challenge.repository.entity;

import java.math.BigDecimal;
import java.util.Date;

public interface SummaryTransaction {
    Date getTransactionDate();
    BigDecimal getTransactionAmount();
    BigDecimal getTransactionBalance();
    Boolean getTransactionStatus();
    String getTypeAccount();
    String getNumberAccount();
    String getClientName();
}
