package com.neoris.challenge.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="date", nullable = false)
    private Date date;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="amount", nullable = false)
    private BigDecimal amount;

    @Column(name="balance", nullable = false)
    private BigDecimal balance;

    @Column(name="status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "clientId", nullable = false, updatable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

}
