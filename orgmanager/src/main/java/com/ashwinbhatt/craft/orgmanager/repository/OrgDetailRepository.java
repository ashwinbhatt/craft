package com.ashwinbhatt.craft.orgmanager.repository;

import com.ashwinbhatt.craft.orgmanager.models.OrgDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrgDetailRepository extends JpaRepository<OrgDetail, String> {
    public Optional<OrgDetail> findByLegalName(String legalName);


}
