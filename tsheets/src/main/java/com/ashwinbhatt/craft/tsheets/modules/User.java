package com.ashwinbhatt.craft.tsheets.modules;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "orgId")
    private OrgDetail orgDetail;

    @Column(unique = true)
    private String userName;

}
