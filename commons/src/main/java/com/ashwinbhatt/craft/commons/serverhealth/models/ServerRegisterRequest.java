package com.ashwinbhatt.craft.commons.serverhealth.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServerRegisterRequest {

    @NotNull
    private String serverId;
    @NotNull
    private String productId;

}
