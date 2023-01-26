package com.neoris.challenge.api.v1.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Transaction {
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
