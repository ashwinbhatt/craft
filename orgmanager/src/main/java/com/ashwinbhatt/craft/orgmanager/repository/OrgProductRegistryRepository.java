package com.ashwinbhatt.craft.orgmanager.repository;

import com.ashwinbhatt.craft.orgmanager.models.OrgProductRegistry;
import com.ashwinbhatt.craft.orgmanager.models.OrgProductRegistryPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrgProductRegistryRepository extends JpaRepository<OrgProductRegistry, OrgProductRegistryPk> {

    public List<OrgProductRegistry> findByOrgProductRegistryPkOrgId(String orgId);
}
