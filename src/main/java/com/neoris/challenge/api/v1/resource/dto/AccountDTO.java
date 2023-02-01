package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.challenge.api.v1.resource.groupvalidation.Create;
import com.neoris.challenge.api.v1.resource.groupvalidation.Update;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    private int id;

    @NotEmpty(groups = Create.class)
    private String number;

    @NotEmpty(groups = Create.class)
    @Pattern(regexp = "Ahorro|Corriente", groups = {Create.class, Update.class})
    private String type;

    @NotNull(groups = Create.class)
    @DecimalMin(value = "0", groups = {Create.class, Update.class})
    private BigDecimal balance;

    @NotNull(groups = Create.class)
    private Boolean status;

    @NotNull(groups = Create.class)
    @Positive(groups = Create.class)
    private Integer clientId;

    private ClientDTO client;
}
