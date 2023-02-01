package com.neoris.challenge.api.v1.resource;

import com.neoris.challenge.api.v1.resource.dto.AccountDTO;
import com.neoris.challenge.api.v1.resource.groupvalidation.Create;
import com.neoris.challenge.api.v1.resource.groupvalidation.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Validated
@RequestMapping(value = "/api/accounts/v1/account")
public interface AccountResource {

    @GetMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    AccountDTO getAccount(@PathVariable("accountId") @NotNull Integer accountId);

    @PutMapping(value = "/{accountId}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateAccount(
            @PathVariable("accountId") @NotNull Integer accountId,
            @RequestBody @Validated({Update.class, Default.class}) AccountDTO account);

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createAccount(@RequestBody @Validated({Create.class, Default.class}) AccountDTO account);

    @DeleteMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteAccount(@PathVariable("accountId") @NotNull Integer accountId);
}
