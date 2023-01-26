package com.neoris.challenge.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
public class Client extends Person {

    @Column(name="password")
    private String password;

    @Column(name="status")
    private Boolean status;
}
