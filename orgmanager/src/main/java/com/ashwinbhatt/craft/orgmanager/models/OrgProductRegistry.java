package com.ashwinbhatt.craft.orgmanager.models;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Entity
public class OrgProductRegistry {

    @Id
    @Embedded
    private OrgProductRegistryPk orgProductRegistryPk;

}
