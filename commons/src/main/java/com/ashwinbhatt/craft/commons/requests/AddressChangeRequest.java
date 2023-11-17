package com.ashwinbhatt.craft.commons.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressChangeRequest {

    private String orgId;
    private String newAddress;

}
