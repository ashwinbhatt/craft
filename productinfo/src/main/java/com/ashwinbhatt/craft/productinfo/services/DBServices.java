package com.ashwinbhatt.craft.productinfo.services;

import com.ashwinbhatt.craft.productinfo.exceptions.DBServicesExceptions;
import com.ashwinbhatt.craft.productinfo.models.ProductType;
import com.ashwinbhatt.craft.productinfo.models.ServerInfo;
import com.ashwinbhatt.craft.productinfo.models.ServerStatus;
import com.ashwinbhatt.craft.productinfo.repositories.ProductTypesRepository;
import com.ashwinbhatt.craft.productinfo.repositories.ServerInfoRepository;
import com.ashwinbhatt.craft.productinfo.repositories.ServerStatusRepository;
import lombok.NonNull;
import org.apache.catalina.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DBServices {

    private final static Logger logger = LoggerFactory.getLogger(DBServices.class);

    private final ServerInfoRepository serverInfoRepository;
    private final ProductTypesRepository productTypesRepository;
    private final ServerStatusRepository serverStatusRepository;

    public DBServices(ServerInfoRepository serverInfoRepository, ProductTypesRepository productTypesRepository, ServerStatusRepository serverStatusRepository) {
        this.serverInfoRepository = serverInfoRepository;
        this.serverStatusRepository = serverStatusRepository;
        this.productTypesRepository = productTypesRepository;
    }

    // CRUD on ServerInfo
    public ServerInfo createServerInfo(@NonNull ServerInfo serverInfo) throws DBServicesExceptions {
        String productId = serverInfo.getProductType().getProductId();
        if(!productExistById(productId)) {
            throw new DBServicesExceptions(String.format("Product by id %s doesn't exist", productId));
        }
        return this.serverInfoRepository.save(serverInfo);
    }

    public ServerInfo readServerInfo(@NonNull String serverId) throws DBServicesExceptions {
        Optional<ServerInfo> serverInfoOptional = this.serverInfoRepository.findById(serverId);
        if(serverInfoOptional.isEmpty()) {
            logger.info(String.format("No record for server with id: %s", serverId));
            throw new DBServicesExceptions(String.format("No record for server with id: %s", serverId));
        }
        return serverInfoOptional.get();
    }

    public ServerInfo updateServerInfo(@NonNull ServerInfo serverInfo) throws DBServicesExceptions {
        String productId = serverInfo.getProductType().getProductId();
        ServerInfo savedServerInfo = readServerInfo(serverInfo.getServerId());
        if(!productExistById(productId)) {
            throw new DBServicesExceptions(String.format("Product by id %s doesn't exist", productId));
        }
        savedServerInfo.setProductType(serverInfo.getProductType());
        return serverInfoRepository.save(savedServerInfo);
    }

    public ServerInfo deleteServerInfo(@NonNull String serverId) throws DBServicesExceptions {
        ServerInfo deleteRec = readServerInfo(serverId);
        serverInfoRepository.deleteById(serverId);
        return deleteRec;
    }

    public boolean productExistById(@NonNull String productId) {
        return productTypesRepository.existsById(productId);
    }

    public boolean productExistByName(@NonNull String productName) {
        return productTypesRepository.findByProductName(productName) != null;
    }

    // CRUD on ProductType
    public ProductType createProductType(@NonNull ProductType productType) throws DBServicesExceptions {
        if(productExistById(productType.getProductId())) {
            throw new DBServicesExceptions(String.format("Product by id %s already exist", productType.getProductId()));
        }
        if(productExistByName(productType.getProductName())) {
            throw new DBServicesExceptions(String.format("Product by name %s already exist", productType.getProductName()));
        }
        return productTypesRepository.save(productType);
    }

    public ProductType readProductType(@NonNull String productId) throws DBServicesExceptions {
        Optional<ProductType> productTypeOptional = productTypesRepository.findById(productId);
        if(productTypeOptional.isEmpty()) {
            throw new DBServicesExceptions(String.format("Product by id %s doesn't exist", productId));
        }
        return productTypeOptional.get();
    }

    public ProductType updateProductType(@NonNull ProductType productType) throws DBServicesExceptions {
        ProductType savedProductType = readProductType(productType.getProductId());
        savedProductType.setProductDescription(productType.getProductDescription());
        return productTypesRepository.save(savedProductType);
    }

    public ProductType deleteProdctType(@NonNull String productId) throws DBServicesExceptions {
        // check if product exist
        ProductType toBeDeletedProduct = readProductType(productId);
        // No server should be of this type
        List<ServerInfo> serverOfProduct = serverInfoRepository.findByProductType(toBeDeletedProduct);
        if(serverOfProduct.size() > 0) {
            throw new DBServicesExceptions(String.format("Cannot remove product %s, as its being used servers. Make sure to remove all dependent server first!", productId));
        }
        productTypesRepository.deleteById(productId);
        return toBeDeletedProduct;
    }

    // CR on server status, UPDATE and delete funcionality is not required by this project.
    public ServerStatus createServerStatus(@NonNull ServerStatus serverStatus) {
        return serverStatusRepository.save(serverStatus);
    }

    public ServerStatus readServerStatus(@NonNull String serverId) throws DBServicesExceptions {
        Optional<ServerStatus> serverStatusOptional = serverStatusRepository.findById(serverId);
        if(serverStatusOptional.isEmpty()) {
            throw new DBServicesExceptions(String.format("Server status for serverId: %s, not found!"));
        }
        return serverStatusOptional.get();
    }

}
