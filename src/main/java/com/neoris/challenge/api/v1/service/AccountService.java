package com.neoris.challenge.api.v1.service;

import com.neoris.challenge.api.v1.service.model.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccount(Integer id);
    void createAccount(Account account);
    void updateAccount(Integer id, Account account);
    void deleteAccount(Integer id);
}
