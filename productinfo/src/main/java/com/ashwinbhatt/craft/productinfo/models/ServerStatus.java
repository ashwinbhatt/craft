package com.ashwinbhatt.craft.productinfo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ServerStatus {

    @Id
    private String serverId;

    @Setter
    private ServerStatusEnum serverStatus;

}
