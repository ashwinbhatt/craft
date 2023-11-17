package com.ashwinbhatt.craft.productinfo.repositories;

import com.ashwinbhatt.craft.productinfo.models.ProductType;
import com.ashwinbhatt.craft.productinfo.models.ServerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerInfoRepository extends JpaRepository<ServerInfo, String> {

    public List<ServerInfo> findByProductType(ProductType productType);
}
