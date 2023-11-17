package com.ashwinbhatt.craft.quickbooks.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountRequest {

    @NotNull
    private String orgId;
    @NotNull
    private String accountName;
    private String accountType;
    private String parentAccountId;
    private Double balance;
    private String currency;
    @NotNull
    private Boolean isActive;

}
