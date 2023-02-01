package com.neoris.challenge.service;

import com.neoris.challenge.api.v1.service.AccountService;
import com.neoris.challenge.api.v1.service.model.Account;
import com.neoris.challenge.repository.AccountRepository;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.mapper.RepositoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Override
    public Optional<Account> getAccount(Integer id) {
        log.info("Retrieving account info by ID: accountId={}", id);
        Optional<Account> accountFound = accountRepository.findById(id)
                .map(account -> repositoryMapper.map(account, Account.class));

        log.info("Response account info by ID: account={}", accountFound);
        if (accountFound.isEmpty() || (accountFound.isPresent() && !accountFound.get().getStatus())) {
            log.error("Account not found: accountId={}", id);
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", id));
        }

        return accountFound;
    }

    @Override
    public void createAccount(Account account) {
        com.neoris.challenge.repository.entity.Account newAccount;
        log.info("Creating account, retrieving client by ID: clientId={}", account.getClientId());
        Optional<com.neoris.challenge.repository.entity.Client> client = clientRepository.findById(account.getClientId());
        if (client.isPresent()) {
            newAccount = repositoryMapper.map(account, com.neoris.challenge.repository.entity.Account.class);
            newAccount.setClient(client.get());
        } else {
            log.error("Creating account, client not found: clientId={}", account.getClientId());
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", account.getClientId()));
        }
        com.neoris.challenge.repository.entity.Account savedAccount = accountRepository.save(newAccount);
        log.info("Creating account, account saved: accountId={}", savedAccount.getId());
    }

    @Override
    public void updateAccount(Integer id, Account account) {
        log.info("Update account, retrieving account by ID: accountId={}", id);
        Optional<com.neoris.challenge.repository.entity.Account> accountUpdate = accountRepository.findById(id);
        if (accountUpdate.isPresent() && accountUpdate.get().getStatus()) {
            Optional.ofNullable(account.getNumber()).ifPresent(accountUpdate.get()::setNumber);
            Optional.ofNullable(account.getType()).ifPresent(accountUpdate.get()::setType);

            accountRepository.save(accountUpdate.get());
            log.info("Update account, account updated: accountId={}", id);
        } else {
            log.error("Account not found: accountId={}", id);
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", id));
        }
    }

    @Override
    public void deleteAccount(Integer id) {
        log.info("Remove account, retrieving account by ID: accountId={}", id);
        Optional<com.neoris.challenge.repository.entity.Account> accountRemove = accountRepository.findById(id);
        if (accountRemove.isEmpty()) {
            log.error("Account not found: accountId={}", id);
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", id));
        }
        accountRemove.get().setStatus(false);
        accountRepository.save(accountRemove.get());
        log.info("Remove account, account removed: accountId={}", id);
    }
}
