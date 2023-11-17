package com.ashwinbhatt.craft.quickbooks.utils;

import com.ashwinbhatt.craft.quickbooks.models.Accounts;
import com.ashwinbhatt.craft.quickbooks.requests.AccountRequest;

public class AppUtils {

    public static Accounts convertAccountRequest(AccountRequest accountsRequests) {
        return new Accounts(null, accountsRequests.getOrgId(), accountsRequests.getAccountName(), accountsRequests.getAccountType(), accountsRequests.getBalance(), accountsRequests.getCurrency(), accountsRequests.getIsActive());
    }
}
