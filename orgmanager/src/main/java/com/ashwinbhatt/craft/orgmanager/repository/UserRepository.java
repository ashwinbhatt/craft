package com.ashwinbhatt.craft.orgmanager.repository;

import com.ashwinbhatt.craft.orgmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    public User findByUserName(String userName);
}
