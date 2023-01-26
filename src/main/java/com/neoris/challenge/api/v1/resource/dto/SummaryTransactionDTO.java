package com.neoris.challenge.api.v1.resource.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SummaryTransactionDTO {
    private Date date;
    private BigDecimal amount;
    private BigDecimal balance;
    private Boolean status;
    private String typeAccount;
    private String numberAccount;
    private String clientName;
}
