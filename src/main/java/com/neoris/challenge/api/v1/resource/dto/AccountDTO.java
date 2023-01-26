package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    private int id;
    private String number;
    private String type;
    private BigDecimal balance;
    private Boolean status;
    private Integer clientId;
    private ClientDTO client;
}
