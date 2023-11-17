package com.ashwinbhatt.craft.quickbooks.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private String orgId;

    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false)
    private String accountType;

    private Double balance;

    private String currency;

    @Column(nullable = false)
    private Boolean isActive;
}
