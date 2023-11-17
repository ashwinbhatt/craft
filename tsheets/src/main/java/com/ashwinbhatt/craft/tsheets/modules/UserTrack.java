package com.ashwinbhatt.craft.tsheets.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTrack {

    @Id
    private String userId;

    private Boolean isOnline;

}
