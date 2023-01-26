package com.neoris.challenge.api.v1.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private int id;
    private String number;
    private String type;
    private BigDecimal balance;
    private Boolean status;
    private Integer clientId;
    private Client client;
}
