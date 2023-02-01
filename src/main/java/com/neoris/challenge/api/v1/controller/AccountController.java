package com.neoris.challenge.api.v1.controller;

import com.neoris.challenge.api.v1.resource.dto.AccountDTO;
import com.neoris.challenge.api.v1.resource.AccountResource;
import com.neoris.challenge.api.v1.resource.mapper.DTOMapper;
import com.neoris.challenge.api.v1.service.AccountService;
import com.neoris.challenge.api.v1.service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("AccountController")
public class AccountController implements AccountResource {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DTOMapper dtoMapper;

    @Override
    public AccountDTO getAccount(Integer accountId) {
        return accountService.getAccount(accountId)
                .map(account -> dtoMapper.map(account, AccountDTO.class))
                .orElse(null);
    }

    @Override
    public void updateAccount(Integer accountId, AccountDTO account) {
        Account updateAccount = dtoMapper.map(account, Account.class);
        accountService.updateAccount(accountId, updateAccount);
    }

    @Override
    public void createAccount(AccountDTO account) {
        Account newAccount = dtoMapper.map(account, Account.class);
        accountService.createAccount(newAccount);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountService.deleteAccount(accountId);
    }
}
