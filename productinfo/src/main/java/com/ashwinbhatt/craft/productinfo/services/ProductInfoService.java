package com.ashwinbhatt.craft.productinfo.services;

import com.ashwinbhatt.craft.orgmanager.autoexpcache.IAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import com.ashwinbhatt.craft.productinfo.exceptions.DBServicesExceptions;
import com.ashwinbhatt.craft.productinfo.models.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ProductInfoService.class);

    private final IAutoExpCacheService autoExpCacheService;
    private final DBServices dbServices;

    public ProductInfoService(IAutoExpCacheService autoExpCacheService, DBServices dbServices) {
        this.autoExpCacheService = autoExpCacheService;
        this.dbServices = dbServices;
    }

    public ServerInfo registerServer(@NonNull ServerRegisterRequest serverRegisterRequest) throws DBServicesExceptions, IAutoExpCacheServiceException {
        // fetch producttype
        ProductType productType = dbServices.readProductType(serverRegisterRequest.getProductId());
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setServerId(serverRegisterRequest.getServerId());
        serverInfo.setProductType(productType);
        // make sure that IAutoExpCache don't have entry for serverId
        autoExpCacheService.removeKeyIfPresent(serverInfo.getServerId());
        ServerInfo savedServerInfo = dbServices.createServerInfo(serverInfo);
        ServerStatus serverStatus = dbServices.createServerStatus(new ServerStatus(serverInfo.getServerId(), ServerStatusEnum.UP));
        // Server is now registered, now add entry to IAutoExpCache
        autoExpCacheService.addKey(serverInfo.getServerId());
        return savedServerInfo;
    }

    public ProductType registerProduct(@NonNull ProductType productType) throws DBServicesExceptions {
        return dbServices.createProductType(productType);
    }

}
