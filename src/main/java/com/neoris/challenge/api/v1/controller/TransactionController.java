package com.neoris.challenge.api.v1.controller;

import com.neoris.challenge.api.v1.resource.dto.SummaryTransactionDTO;
import com.neoris.challenge.api.v1.resource.mapper.DTOMapper;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import com.neoris.challenge.api.v1.resource.TransactionResource;
import com.neoris.challenge.api.v1.service.TransactionService;
import com.neoris.challenge.api.v1.service.model.SummaryTransaction;
import com.neoris.challenge.api.v1.service.model.Transaction;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController("TransactionController")
public class TransactionController implements TransactionResource {

    @Autowired
    TransactionService transactionService;

    @Autowired
    DTOMapper dtoMapper;

    @Override
    public TransactionDTO getTransaction(Integer transactionId) {
        return transactionService.getTransaction(transactionId)
                .map(transaction -> dtoMapper.map(transaction, TransactionDTO.class))
                .orElse(null);
    }

    @Override
    public void createTransaction(@NotNull TransactionDTO transaction) {
        Transaction newTransaction = new Transaction();
        newTransaction.setClientId(transaction.getClientId());
        newTransaction.setAccountId(transaction.getAccountId());
        newTransaction.setAmount(transaction.getAmount());
        newTransaction.setType(transaction.getType());
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
                .map(transaction -> {
                    SummaryTransactionDTO summary = new SummaryTransactionDTO();
                    summary.setDate(transaction.getDate());
                    summary.setBalance(transaction.getBalance());
                    summary.setStatus(transaction.getStatus());
                    summary.setClientName(transaction.getClientName());
                    summary.setNumberAccount(transaction.getNumberAccount());
                    summary.setTypeAccount(transaction.getTypeAccount());
                    return summary;
                }).collect(Collectors.toList());
    }
}
