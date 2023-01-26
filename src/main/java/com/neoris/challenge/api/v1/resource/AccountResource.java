package com.neoris.challenge.api.v1.resource;

import com.neoris.challenge.api.v1.resource.dto.AccountDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/accounts/v1/account")
public interface AccountResource {

    @GetMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    AccountDTO getAccount(@PathVariable("accountId") Integer accountId);

    @PutMapping(value = "/{accountId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAccount(@PathVariable("accountId") Integer accountId, @RequestBody AccountDTO account);

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody AccountDTO account);

    @DeleteMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteAccount(@PathVariable("accountId") Integer accountId);
}
