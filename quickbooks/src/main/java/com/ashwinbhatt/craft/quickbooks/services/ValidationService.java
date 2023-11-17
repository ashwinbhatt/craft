package com.ashwinbhatt.craft.quickbooks.services;

import com.ashwinbhatt.craft.quickbooks.repository.AccountsRepository;
import com.ashwinbhatt.craft.quickbooks.services.AppDBServices;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationService {

    private final AppDBServices appDBServices;

    public ValidationService(AppDBServices appDBServices) {
        this.appDBServices = appDBServices;
    }

    public Boolean isAddressChangeAble(@NonNull String orgId) {
        List<Long> activeAccounts = appDBServices.getOrgActiveAccount(orgId);
        return !activeAccounts.isEmpty();
    }

}
