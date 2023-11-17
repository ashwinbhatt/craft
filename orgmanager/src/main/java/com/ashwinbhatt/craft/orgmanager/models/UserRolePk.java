package com.ashwinbhatt.craft.orgmanager.models;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRolePk implements Serializable {

    private String userId;
    private String productId;

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserRolePk) {
            UserRolePk tObject = (UserRolePk) obj;
            return Objects.equals(tObject.getUserId(), userId) &&
                    Objects.equals(tObject.getProductId(), productId);
        }
        return false;
    }
}
