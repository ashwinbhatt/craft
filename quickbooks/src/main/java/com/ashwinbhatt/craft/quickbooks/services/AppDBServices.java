package com.ashwinbhatt.craft.quickbooks.services;

import com.ashwinbhatt.craft.quickbooks.exceptions.AppDBServiceException;
import com.ashwinbhatt.craft.quickbooks.models.Accounts;
import com.ashwinbhatt.craft.quickbooks.repository.AccountsRepository;
import com.ashwinbhatt.craft.quickbooks.repository.OrgDetailRepository;
import com.ashwinbhatt.craft.quickbooks.requests.AccountRequest;
import com.ashwinbhatt.craft.quickbooks.utils.AppUtils;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppDBServices {

    private final AccountsRepository accountsRepository;
    private final OrgDetailRepository orgDetailRepository;

    public AppDBServices(AccountsRepository accountsRepository, OrgDetailRepository orgDetailRepository) {
        this.accountsRepository = accountsRepository;
        this.orgDetailRepository = orgDetailRepository;
    }

    // Operations on AppDBServices
    public Accounts createAccount(@NonNull AccountRequest accountRequest) throws AppDBServiceException {
        String orgId = accountRequest.getOrgId();
        if(!orgDetailRepository.existsById(orgId)) {
            throw new AppDBServiceException(String.format("Organisation %s doesn't exist", orgId));
        }
        Accounts account = AppUtils.convertAccountRequest(accountRequest);
        return accountsRepository.save(account);
    }

    public Accounts getAccount(@NonNull Long accountId) throws AppDBServiceException {
        Optional<Accounts> accounts = accountsRepository.findById(accountId);
        if(accounts.isEmpty()) {
            throw new AppDBServiceException(String.format("Accounts don't have data for account id: %s"));
        }
        return accounts.get();
    }

    public Accounts updateAccount(@NonNull Long accountId, @NonNull AccountRequest accountRequest) throws AppDBServiceException {
        Accounts savedData = getAccount(accountId);
        Accounts requestData = AppUtils.convertAccountRequest(accountRequest);

        if(requestData.getAccountType() != null && !savedData.getAccountType().equals(requestData.getAccountType())){
            savedData.setAccountType(requestData.getAccountType());
        }

        if(requestData.getBalance() != null && !savedData.getBalance().equals(requestData.getBalance())) {
            savedData.setBalance(requestData.getBalance());
        }

        if(requestData.getIsActive() != null && !savedData.getIsActive().equals(requestData.getIsActive())) {
            savedData.setIsActive(requestData.getIsActive());
        }
        return accountsRepository.save(savedData);
    }

    public List<Long> getOrgActiveAccount(@NonNull String orgId) {
        return accountsRepository.findAll().stream()
                .filter(accounts -> {
                    if(accounts.getOrgId().equals(orgId) && accounts.getIsActive().equals(Boolean.TRUE)){
                        return true;
                    }
                    return false;
                }).map(Accounts::getAccountId).collect(Collectors.toList());
    }


}
