package com.neoris.challenge.api.v1.resource;

import com.neoris.challenge.api.v1.resource.dto.SummaryTransactionDTO;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import com.neoris.challenge.api.v1.resource.groupvalidation.Create;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;
import java.util.List;

@Validated
@RequestMapping(value = "/api/transactions/v1/transaction")
public interface TransactionResource {

    @GetMapping(value = "/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    TransactionDTO getTransaction(@PathVariable("transactionId") @NotNull Integer transactionId);

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createTransaction(@RequestBody @Validated({Create.class, Default.class}) TransactionDTO transaction);

    @DeleteMapping(value = "/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTransaction(@PathVariable("transactionId") @NotNull Integer transactionId);

    @GetMapping(value = "/reports")
    List<SummaryTransactionDTO> getSummaryTransactions(
            @RequestParam(value = "clientId") @NotNull @Positive int clientId,
            @RequestParam(value = "initDate") @NotNull @Pattern(regexp="^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$") String initDate,
            @RequestParam(value = "endDate") @NotNull @Pattern(regexp="^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$") String endDate);
}
