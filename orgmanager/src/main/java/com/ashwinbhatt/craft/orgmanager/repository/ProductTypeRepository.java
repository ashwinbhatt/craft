package com.ashwinbhatt.craft.orgmanager.repository;

import com.ashwinbhatt.craft.orgmanager.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, String> {
}
