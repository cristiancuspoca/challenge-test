package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.challenge.api.v1.resource.groupvalidation.Create;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    private int id;

    private Date date;

    @NotEmpty(groups = Create.class)
    @Pattern(regexp = "DEBIT|CREDIT", groups = {Create.class})
    private String type;

    private BigDecimal balance;

    @NotNull(groups = Create.class)
    @DecimalMin(value = "1", groups = {Create.class})
    private BigDecimal amount;

    private Boolean status;

    @NotNull(groups = Create.class)
    @Positive(groups = Create.class)
    private Integer clientId;

    private ClientDTO client;

    @NotNull(groups = Create.class)
    @Positive(groups = Create.class)
    private Integer accountId;

    private AccountDTO account;
}
