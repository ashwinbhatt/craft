package com.ashwinbhatt.craft.orgmanager.models;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.DependsOn;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Entity
public class UserRole {

    @Id
    @EmbeddedId
    private UserRolePk userRolePk;

    private String role;

}
