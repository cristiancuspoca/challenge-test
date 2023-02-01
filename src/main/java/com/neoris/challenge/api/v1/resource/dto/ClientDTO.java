package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.challenge.api.v1.resource.groupvalidation.Create;
import com.neoris.challenge.api.v1.resource.groupvalidation.Update;
import com.neoris.challenge.type.Gender;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private int id;

    @NotEmpty(groups = Create.class)
    private String name;

    private Gender gender;

    @Positive(groups = {Create.class, Update.class})
    private Integer age;

    private String identification;

    @NotEmpty(groups = Create.class)
    private String address;

    @NotEmpty(groups = Create.class)
    private String phone;

    @NotNull(groups = Create.class)
    private Boolean status;

    @NotEmpty(groups = Create.class)
    private String password;
}
