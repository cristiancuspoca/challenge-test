package com.neoris.challenge.api.v1.resource.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.challenge.type.Gender;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private int id;
    private String name;
    private Gender gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    private Boolean status;
    private String password;
}
