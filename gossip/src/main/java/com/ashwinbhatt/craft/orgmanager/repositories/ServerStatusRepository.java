package com.ashwinbhatt.craft.orgmanager.repositories;

import com.ashwinbhatt.craft.orgmanager.models.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerStatusRepository extends JpaRepository<ServerStatus, String> {
}
