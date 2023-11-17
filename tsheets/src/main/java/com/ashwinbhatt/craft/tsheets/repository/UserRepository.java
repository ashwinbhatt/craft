package com.ashwinbhatt.craft.tsheets.repository;

import com.ashwinbhatt.craft.tsheets.modules.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    public User findByUserName(String userName);
}
