package com.example.demo.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "manufacturer_address")
    private String adress;

    public Manufacturer() {
    }

    public Manufacturer(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }
}
