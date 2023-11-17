package com.ashwinbhatt.craft.productinfo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductType {

    @Id
    @NotNull
    private String productId;

    @Column(unique = true)
    @NotNull
    private String productName;

    private String productDescription;
}
