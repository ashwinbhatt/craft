package com.ashwinbhatt.craft.quickbooks.web;

import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import com.ashwinbhatt.craft.quickbooks.exceptions.AppDBServiceException;
import com.ashwinbhatt.craft.quickbooks.models.Accounts;
import com.ashwinbhatt.craft.quickbooks.requests.AccountRequest;
import com.ashwinbhatt.craft.quickbooks.services.AppDBServices;
import com.ashwinbhatt.craft.quickbooks.services.ValidationService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quickbooks")
public class QuickBooksController {

    private final AppDBServices appDBServices;
    private final ValidationService validationService;

    public QuickBooksController(AppDBServices appDBServices, ValidationService validationService) {
        this.appDBServices = appDBServices;
        this.validationService = validationService;
    }

    @PostMapping("/account")
    public Accounts createAccount(@RequestBody AccountRequest accountRequest) throws AppDBServiceException {
        return appDBServices.createAccount(accountRequest);
    }

    @GetMapping("/account/{accountId}")
    public Accounts readAccount(@PathVariable Long accountId) throws AppDBServiceException {
        return appDBServices.getAccount(accountId);
    }

    @PutMapping("/account/{accountId}")
    public Accounts updateAccount(@PathVariable Long accountId, @RequestBody AccountRequest accountRequest) throws AppDBServiceException {
        return appDBServices.updateAccount(accountId, accountRequest);
    }

    // For testing
    @PostMapping("/org/validate")
    public Boolean getOrgActiveAccount(@RequestBody AddressChangeRequest addressChangeRequest) {
        List<Long> activeAccounts = appDBServices.getOrgActiveAccount(addressChangeRequest.getOrgId());
        return activeAccounts.isEmpty();
    }

}
