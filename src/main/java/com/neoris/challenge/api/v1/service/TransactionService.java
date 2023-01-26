package com.neoris.challenge.api.v1.service;

import com.neoris.challenge.api.v1.service.model.SummaryTransaction;
import com.neoris.challenge.api.v1.service.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Optional<Transaction> getTransaction(Integer id);
    void createTransaction(Transaction transaction);
    void deleteTransaction(Integer id);
    List<SummaryTransaction> getSummaryTransactions(int clientId, String initDate, String endDate);
}
