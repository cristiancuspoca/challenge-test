package com.neoris.challenge.api.v1.service.model;

import com.neoris.challenge.type.Gender;
import lombok.Data;

@Data
public class Client {
    private int id;
    private String name;
    private Gender gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}
