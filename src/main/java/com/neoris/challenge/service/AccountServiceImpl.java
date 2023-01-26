package com.neoris.challenge.service;

import com.neoris.challenge.api.v1.service.AccountService;
import com.neoris.challenge.api.v1.service.model.Account;
import com.neoris.challenge.repository.AccountRepository;
import com.neoris.challenge.repository.ClientRepository;
import com.neoris.challenge.repository.mapper.RepositoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
        Optional<Account> accountFound = accountRepository.findById(id)
                .map(account -> repositoryMapper.map(account, Account.class));

        if (accountFound.isEmpty() || (accountFound.isPresent() && !accountFound.get().getStatus())) {
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", id));
        }

        return accountFound;
    }

    @Override
    public void createAccount(Account account) {
        com.neoris.challenge.repository.entity.Account newAccount = new com.neoris.challenge.repository.entity.Account();
        Optional<com.neoris.challenge.repository.entity.Client> client = clientRepository.findById(account.getClientId());
        if (client.isPresent()) {
            newAccount.setNumber(account.getNumber());
            newAccount.setType(account.getType());
            newAccount.setStatus(account.getStatus());
            newAccount.setBalance(account.getBalance());
            newAccount.setClient(client.get());
        } else {
            throw new EntityNotFoundException(String.format("Client not found: clientId=%s", account.getClientId()));
        }
        accountRepository.save(newAccount);
    }

    @Override
    public void updateAccount(Integer id, Account account) {
        Optional<com.neoris.challenge.repository.entity.Account> accountUpdate = accountRepository.findById(id);
        if (accountUpdate.isPresent() && accountUpdate.get().getStatus()) {
            Optional.ofNullable(account.getNumber()).ifPresent(accountUpdate.get()::setNumber);
            Optional.ofNullable(account.getType()).ifPresent(accountUpdate.get()::setType);

            accountRepository.save(accountUpdate.get());
        } else {
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", id));
        }
    }

    @Override
    public void deleteAccount(Integer id) {
        Optional<com.neoris.challenge.repository.entity.Account> accountRemove = accountRepository.findById(id);
        if (accountRemove.isEmpty()) {
            throw new EntityNotFoundException(String.format("Account not found: accountId=%s", id));
        }
        accountRemove.get().setStatus(false);
        accountRepository.save(accountRemove.get());
    }
}
