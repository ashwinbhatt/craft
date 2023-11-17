package com.ashwinbhatt.craft.quickbooks.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class OrgDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orgId;

    private String companyName;

    @Column(unique = true)
    private String legalName;

    private String businessAddress;

    private String legalAddress;

    private String taxIdentifier;

    private String email;

    private String website;

}
