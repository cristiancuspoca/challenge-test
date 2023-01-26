package com.neoris.challenge.api.v1.resource;

import com.neoris.challenge.api.v1.resource.dto.SummaryTransactionDTO;
import com.neoris.challenge.api.v1.resource.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/transactions/v1/transaction")
public interface TransactionResource {

    @GetMapping(value = "/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    TransactionDTO getTransaction(@PathVariable("transactionId") Integer transactionId);

    @PostMapping(value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    void createTransaction(@RequestBody TransactionDTO transaction);

    @DeleteMapping(value = "/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTransaction(@PathVariable("transactionId") Integer transactionId);

    @GetMapping(value = "/reports")
    List<SummaryTransactionDTO> getSummaryTransactions(
            @RequestParam(value = "clientId", required = false) int clientId,
            @RequestParam(value = "initDate", required = false) String initDate,
            @RequestParam(value = "endDate", required = false) String endDate);
}
