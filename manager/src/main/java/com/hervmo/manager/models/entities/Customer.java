package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    @Id
    private Long customerId;

    private String customerFirstname;
    private String customerNumber;
    private String customerLastname;
    @OneToOne
    private Users user;
    @OneToMany
    private List<Articles> articlesList = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL
    )
    private List<Boxes> boxesList = new ArrayList<>();
}
