package com.ashwinbhatt.craft.orgmanager.controller;

import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import com.ashwinbhatt.craft.orgmanager.exceptions.DbServiceException;
import com.ashwinbhatt.craft.orgmanager.models.*;
import com.ashwinbhatt.craft.orgmanager.service.DbServices;
import com.ashwinbhatt.craft.orgmanager.service.OrgManagerServer;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class OrgManagerController {

    private final DbServices dbServices;
    private final OrgManagerServer orgManagerServer;

    public OrgManagerController(DbServices dbServices, OrgManagerServer orgManagerServer) {
        this.dbServices = dbServices;
        this.orgManagerServer = orgManagerServer;
    }

    @PostMapping("/orgs")
    public OrgDetail createOrgDetail(@NotNull @RequestBody OrgDetail orgDetail) throws DbServiceException {
        return dbServices.createOrdDetail(orgDetail);
    }

    @GetMapping("/orgs/{orgId}")
    public OrgDetail getOrgDetailById(@NotNull @PathVariable String orgId) throws DbServiceException {
        return dbServices.getOrgDetailById(orgId);
    }

    @PutMapping("/orgs")
    public OrgDetail updateOrgDetail(@NotNull @RequestBody OrgDetail orgDetail) throws DbServiceException {
        return dbServices.updateOrgDetail(orgDetail);
    }


    @PostMapping("/user")
    public User createUser(@NotNull @RequestBody UserRequest userRequest) throws DbServiceException {
        return dbServices.createUser(userRequest);
    }

    @GetMapping("/user/{userId}")
    public User getUser(@NotNull @PathVariable String userId) throws DbServiceException {
        return dbServices.readUser(userId);
    }

    @PutMapping("/user/{userId}")
    public User updateUser(@NotNull @PathVariable String userId, @NotNull @RequestBody UserRequest userRequest) throws DbServiceException {
        return dbServices.updateUser(userId, userRequest);
    }

    @DeleteMapping("/user/{userId}")
    public User deleteUser(@NotNull @PathVariable String userId) throws DbServiceException {
        return dbServices.deleteUser(userId);
    }


    @PostMapping("/user/role")
    public UserRole createUserRole(@NotNull @RequestBody UserRoleRequest userRoleRequest) throws DbServiceException {
        return dbServices.createUserRole(userRoleRequest);
    }

    @GetMapping("/user/role/{userId}")
    public UserRole readUserRole(@NotNull @PathVariable String userId, @NotNull @RequestParam("productId") String productId) throws DbServiceException {
        return dbServices.readUserRole(new UserRolePk(userId, productId));
    }

    @PutMapping("/user/role")
    public UserRole updateUserRole(@NotNull @RequestBody UserRoleRequest userRoleRequest) throws DbServiceException {
        return dbServices.updateUserRole(userRoleRequest);
    }

    @PostMapping("/org/register")
    public OrgProductRegistry createOrgProductRegistry(@NotNull @RequestBody OrgProductRegistryRequest orgProductRegistryRequest) throws DbServiceException {
        return dbServices.createOrgProductRegistry(orgProductRegistryRequest);
    }

    @GetMapping("/org/register/{orgId}")
    public OrgProductRegistry readOrgProductRegistry(@NotNull @PathVariable String orgId,@NotNull @PathParam("productId") String productId) throws DbServiceException {
        OrgProductRegistryPk orgProductRegistryPk = new OrgProductRegistryPk(orgId, productId);
        return dbServices.readOrgProductRegistry(orgProductRegistryPk);
    }

    @PutMapping("/org/register")
    public OrgProductRegistry updateOrgProductRegistry(@NotNull @RequestBody OrgProductRegistryRequest orgProductRegistryRequest) throws DbServiceException {
        return dbServices.updateOrgProductRegistry(orgProductRegistryRequest);
    }

    @GetMapping("/org/products/{orgId}")
    public List<String> orgGetAllRegisteredProducts(@NotNull @PathVariable String orgId) throws DbServiceException {
        return dbServices.getAllProductIdForOrgId(orgId);
    }

    @PutMapping("/org/address")
    public OrgDetail updateOrgAddress(@NotNull @RequestBody AddressChangeRequest addressChangeRequest) throws ExecutionException, DbServiceException, InterruptedException {
        return orgManagerServer.updateOrgAddress(addressChangeRequest);
    }

}
