package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.challenge.api.v1.service.model.Account;
import com.neoris.challenge.api.v1.service.model.Client;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private int id;
    private Date date;
    private String type;
    private BigDecimal balance;
    private BigDecimal amount;
    private Boolean status;
    private Integer clientId;
    private Client client;
    private Integer accountId;
    private Account account;
}
