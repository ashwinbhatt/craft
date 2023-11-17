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
public class OrgProductRegistryPk implements Serializable {

    private String orgId;
    private String productId;

    @Override
    public int hashCode() {
        return Objects.hash(orgId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof OrgProductRegistryPk) {
            OrgProductRegistryPk tObj = (OrgProductRegistryPk) obj;
            return Objects.equals(tObj.getProductId(), productId) &&
                    Objects.equals(tObj.getOrgId(), orgId);
        }
        return false;
    }
}
