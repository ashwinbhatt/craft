package com.ashwinbhatt.craft.quickbooks.repository;

import com.ashwinbhatt.craft.quickbooks.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Accounts findByAccountName(String accountName);


}
