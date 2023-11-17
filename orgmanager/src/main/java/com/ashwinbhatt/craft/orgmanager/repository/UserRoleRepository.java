package com.ashwinbhatt.craft.orgmanager.repository;

import com.ashwinbhatt.craft.orgmanager.models.UserRolePk;
import com.ashwinbhatt.craft.orgmanager.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePk> {
}
