package com.ashwinbhatt.craft.orgmanager.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrgProductRegistryRequest {

    private String orgId;
    private String productId;

}
