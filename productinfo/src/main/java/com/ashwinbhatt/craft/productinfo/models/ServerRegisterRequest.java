package com.ashwinbhatt.craft.productinfo.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ServerRegisterRequest {

    @NotNull
    private String serverId;
    @NotNull
    private String productId;

}
