package com.ashwinbhatt.craft.orgmanager.utils;

import com.ashwinbhatt.craft.orgmanager.models.*;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static Map<String, String> productAddressValidationUrlMap;

    static {
        productAddressValidationUrlMap = new HashMap<>();
        productAddressValidationUrlMap.put("quickbooks", "http://localhost:8085/quickbooks/org/validate");
        productAddressValidationUrlMap.put("tsheets",    "http://localhost:8086/tsheets/org/validate");
        productAddressValidationUrlMap.put("payrolls",   "http://localhost:8087/payrolls/org/validate");
    }


    public static UserRole userRoleRequestToUserRole(UserRoleRequest userRoleRequest) {
        UserRolePk userRolePk = new UserRolePk(userRoleRequest.getUserId(), userRoleRequest.getProductId());
        UserRole userRole = new UserRole(userRolePk, userRoleRequest.getRole());
        return userRole;
    }

    public static OrgProductRegistry orgProductRegistryRequestToOrgProductRegistry(OrgProductRegistryRequest orgProductRegistryRequest) {
        OrgProductRegistryPk orgProductRegistryPk = new OrgProductRegistryPk(orgProductRegistryRequest.getOrgId(), orgProductRegistryRequest.getProductId());
        return new OrgProductRegistry(orgProductRegistryPk);
    }

    public static String getUrl(String productId) {
        return productAddressValidationUrlMap.get(productId);
    }


}
