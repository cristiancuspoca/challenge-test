package com.neoris.challenge.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="gender", length=1)
    private String gender;

    @Column(name="age")
    private Integer age;

    @Column(name="identification")
    private String identification;

    @Column(name="address")
    private String address;

    @Column(name="phone")
    private String phone;
}
