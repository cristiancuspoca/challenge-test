package com.neoris.challenge.service;

import com.neoris.challenge.api.v1.service.TransactionService;
import com.neoris.challenge.api.v1.service.model.SummaryTransaction;
import com.neoris.challenge.api.v1.service.model.Transaction;
import com.neoris.challenge.exception.TransactionException;
import com.neoris.challenge.repository.AccountRepository;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.TransactionRepository;
import com.neoris.challenge.repository.mapper.RepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionContext transactionContext;

    @Autowired
    RepositoryMapper repositoryMapper;

    @Override
    public Optional<Transaction> getTransaction(Integer id) {
        Optional<Transaction> transactionFound = transactionRepository.findById(id)
                .map(transaction -> repositoryMapper.map(transaction, Transaction.class));

        if (transactionFound.isEmpty() || (transactionFound.isPresent() && !transactionFound.get().getStatus())) {
            throw new EntityNotFoundException(String.format("Transaction not found: transactionId=%s", id));
        }
        return transactionFound;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        com.neoris.challenge.repository.entity.Transaction transactionNew = new com.neoris.challenge.repository.entity.Transaction();
        Optional<com.neoris.challenge.repository.entity.Client> client = clientRepository.findById(transaction.getClientId());
        Optional<com.neoris.challenge.repository.entity.Account> account = accountRepository.findById(transaction.getAccountId());
        if (client.isEmpty()) {
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", transaction.getClientId()));
        }
        if (account.isEmpty()) {
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", transaction.getAccountId()));
        }

        transactionNew.setStatus(true);
        transactionNew.setDate(new Date());
        transactionNew.setClient(client.get());
        transactionNew.setAmount(transaction.getAmount());
        transactionNew.setType(transaction.getType());
        if (transaction.getType().equals("DEBIT")) {
            transactionContext.selectTransactionType(new DebitTransaction());
        } else if (transaction.getType().equals("CREDIT")) {
            transactionContext.selectTransactionType(new CreditTransaction());
        } else {
            throw new TransactionException("Transactions type allowed: DEBIT, CREDIT");
        }
        BigDecimal newBalance = transactionContext.apply(account.get().getBalance(), transaction.getAmount());
        transactionNew.setBalance(newBalance);
        account.get().setBalance(newBalance);
        transactionNew.setAccount(account.get());

        transactionRepository.save(transactionNew);
    }

    @Override
    public void deleteTransaction(Integer id) {
        Optional<com.neoris.challenge.repository.entity.Transaction> transactionToRemove= transactionRepository.findById(id);
        if (transactionToRemove.isEmpty()) {
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", id));
        }
        transactionToRemove.get().setStatus(false);
        transactionRepository.save(transactionToRemove.get());
    }

    @Override
    public List<SummaryTransaction> getSummaryTransactions(int clientId, String initDate, String endDate) {
        return transactionRepository.getSummaryTransactions(clientId, initDate, endDate)
                .stream().map(transaction -> {
                    SummaryTransaction summary = new SummaryTransaction();
                    summary.setDate(transaction.getTransactionDate());
                    summary.setAmount(transaction.getTransactionAmount());
                    summary.setBalance(transaction.getTransactionBalance());
                    summary.setStatus(transaction.getTransactionStatus());
                    summary.setClientName(transaction.getClientName());
                    summary.setNumberAccount(transaction.getNumberAccount());
                    summary.setTypeAccount(transaction.getTypeAccount());
                    return summary;
                }).collect(Collectors.toList());
    }
}
