package com.neoris.challenge.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="number", unique = true)
    private String number;

    @Column(name="type")
    private String type;

    @Column(name="balance")
    private BigDecimal balance;

    @Column(name="status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false, updatable = false)
    private Client client;
}
