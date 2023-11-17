package com.ashwinbhatt.craft.productinfo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
public class ServerInfo {

    @Id
    private String serverId;

    @ManyToOne
    @JoinColumn(name = "productName")
    private ProductType productType;

}
