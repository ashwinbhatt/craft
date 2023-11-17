package com.ashwinbhatt.craft.productinfo.repositories;

import com.ashwinbhatt.craft.productinfo.models.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerStatusRepository extends JpaRepository<ServerStatus, String> {
}
