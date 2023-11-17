package com.ashwinbhatt.craft.productinfo.repositories;

import com.ashwinbhatt.craft.productinfo.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypesRepository extends JpaRepository<ProductType, String> {

    public ProductType findByProductName(String productName);

}
