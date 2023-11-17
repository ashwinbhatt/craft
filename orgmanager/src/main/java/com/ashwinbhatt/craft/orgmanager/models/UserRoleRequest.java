package com.ashwinbhatt.craft.orgmanager.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequest {

    @NotNull
    private String userId;
    @NotNull
    private String productId;
    @NotNull
    private String role;

}
