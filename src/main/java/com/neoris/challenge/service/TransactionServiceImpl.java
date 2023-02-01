package com.neoris.challenge.service;

import com.neoris.challenge.api.v1.service.TransactionService;
import com.neoris.challenge.api.v1.service.model.SummaryTransaction;
import com.neoris.challenge.api.v1.service.model.Transaction;
import com.neoris.challenge.exception.TransactionException;
import com.neoris.challenge.repository.AccountRepository;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.TransactionRepository;
import com.neoris.challenge.repository.mapper.RepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionContext transactionContext;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public Optional<Transaction> getTransaction(Integer id) {
        log.info("Retrieving transaction info by ID: transactionId={}", id);
        Optional<Transaction> transactionFound = transactionRepository.findById(id)
                .map(transaction -> repositoryMapper.map(transaction, Transaction.class));

        if (transactionFound.isEmpty() || (transactionFound.isPresent() && !transactionFound.get().getStatus())) {
            log.error("Transaction not found: transactionId={}", id);
            throw new EntityNotFoundException(String.format("Transaction not found: transactionId=%s", id));
        }
        log.info("Response transaction info by ID: transaction={}", transactionFound);
        return transactionFound;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        log.info("Creating transaction: transaction={}", transaction);
        com.neoris.challenge.repository.entity.Transaction transactionNew = new com.neoris.challenge.repository.entity.Transaction();
        Optional<com.neoris.challenge.repository.entity.Client> client = clientRepository.findById(transaction.getClientId());
        Optional<com.neoris.challenge.repository.entity.Account> account = accountRepository.findById(transaction.getAccountId());
        if (client.isEmpty()) {
            log.error("Client not found: clientId={}", transaction.getClientId());
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", transaction.getClientId()));
        }
        if (account.isEmpty()) {
            log.error("Account not found: accountId={}", transaction.getAccountId());
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", transaction.getAccountId()));
        }

        transactionNew.setStatus(true);
        transactionNew.setDate(new Date());
        transactionNew.setClient(client.get());
        transactionNew.setAmount(transaction.getAmount());
        transactionNew.setType(transaction.getType());
        if (transaction.getType().equals("DEBIT")) {
            log.info("Applying DEBIT strategy: clientId={}, accountId={}, amount={}",
                    transaction.getClientId(), transaction.getAccountId(), transaction.getAmount());
            transactionContext.selectTransactionType(new DebitTransaction());
        } else if (transaction.getType().equals("CREDIT")) {
            transactionContext.selectTransactionType(new CreditTransaction());
        } else {
            log.error("Transaction type not allowed: type={}", transaction.getType());
            throw new TransactionException("Transactions type allowed: DEBIT, CREDIT");
        }
        BigDecimal newBalance = transactionContext.apply(account.get().getBalance(), transaction.getAmount());
        transactionNew.setBalance(newBalance);
        account.get().setBalance(newBalance);
        transactionNew.setAccount(account.get());

        transactionRepository.save(transactionNew);
        log.info("Transaction saved: transaction={}", transactionNew);
    }

    @Override
    public void deleteTransaction(Integer id) {
        log.info("Deleting transaction: transactionId={}", id);
        Optional<com.neoris.challenge.repository.entity.Transaction> transactionToRemove= transactionRepository.findById(id);
        if (transactionToRemove.isEmpty()) {
            log.error("Transaction not found: transactionId={}", id);
            throw new EntityNotFoundException(String.format("Transaction not found: transactionId=%s", id));
        }
        transactionToRemove.get().setStatus(false);
        transactionRepository.save(transactionToRemove.get());
        log.info("Transaction removed: transactionId={}", id);
    }

    @Override
    public List<SummaryTransaction> getSummaryTransactions(int clientId, String initDate, String endDate) {
        log.info("Getting transaction report: clientId={}, initDate={}, endDate={}", clientId, initDate, endDate);
        return transactionRepository.getSummaryTransactions(clientId, initDate, endDate)
                .stream()
                .map(transaction -> repositoryMapper.map(transaction, SummaryTransaction.class))
                .collect(Collectors.toList());
    }
}
