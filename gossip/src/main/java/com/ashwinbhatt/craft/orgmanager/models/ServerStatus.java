package com.ashwinbhatt.craft.orgmanager.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ServerStatus {

    @Id
    private String serverId;

    @Setter
    private ServerStatusEnum serverStatus;

}
