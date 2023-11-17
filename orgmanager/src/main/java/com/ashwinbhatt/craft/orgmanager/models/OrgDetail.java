package com.ashwinbhatt.craft.orgmanager.models;


import jakarta.persistence.*;
import lombok.*;


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
