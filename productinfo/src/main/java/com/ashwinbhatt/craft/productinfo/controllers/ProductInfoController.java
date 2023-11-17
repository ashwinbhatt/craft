package com.ashwinbhatt.craft.productinfo.controllers;

import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import com.ashwinbhatt.craft.productinfo.exceptions.DBServicesExceptions;
import com.ashwinbhatt.craft.productinfo.models.ProductType;
import com.ashwinbhatt.craft.productinfo.models.ServerInfo;
import com.ashwinbhatt.craft.productinfo.models.ServerRegisterRequest;
import com.ashwinbhatt.craft.productinfo.services.ProductInfoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductInfoController {
    private final static Logger logger = LoggerFactory.getLogger(ProductInfoController.class);

    private final ProductInfoService productInfoService;

    public ProductInfoController(ProductInfoService productInfoServices) {
        this.productInfoService = productInfoServices;
    }

    @PostMapping("/server/register")
    public ServerInfo registerServer(@Valid @RequestBody ServerRegisterRequest serverRegisterRequest) throws IAutoExpCacheServiceException, DBServicesExceptions {
        return productInfoService.registerServer(serverRegisterRequest);
    }

    @PostMapping("/product/register")
    public ProductType registerProduct(@Valid @RequestBody ProductType productType) throws DBServicesExceptions {
        return productInfoService.registerProduct(productType);
    }

}
