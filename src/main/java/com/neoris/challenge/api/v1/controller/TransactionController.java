package com.neoris.challenge.api.v1.controller;

import com.neoris.challenge.api.v1.resource.dto.SummaryTransactionDTO;
import com.neoris.challenge.api.v1.resource.mapper.DTOMapper;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import com.neoris.challenge.api.v1.resource.TransactionResource;
import com.neoris.challenge.api.v1.service.TransactionService;
import com.neoris.challenge.api.v1.service.model.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("TransactionController")
public class TransactionController implements TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DTOMapper dtoMapper;

    @Override
    public TransactionDTO getTransaction(Integer transactionId) {
        return transactionService.getTransaction(transactionId)
                .map(transaction -> dtoMapper.map(transaction, TransactionDTO.class))
                .orElse(null);
    }

    @Override
    public void createTransaction(@NotNull TransactionDTO transaction) {
        Transaction newTransaction = dtoMapper.map(transaction, Transaction.class);
        transactionService.createTransaction(newTransaction);
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    @Override
    public List<SummaryTransactionDTO> getSummaryTransactions(int clientId, String initDate, String endDate) {
        return transactionService.getSummaryTransactions(clientId, initDate, endDate)
                .stream()
                .map(transaction -> dtoMapper.map(transaction, SummaryTransactionDTO.class))
                .collect(Collectors.toList());
    }
}
