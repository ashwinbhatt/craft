package com.ashwinbhatt.craft.orgmanager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductType {

    @Id
    private String productId;

    @Column(unique = true)
    private String productName;

    private String productDescription;
}
