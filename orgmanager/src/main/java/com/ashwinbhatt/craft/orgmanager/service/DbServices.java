package com.ashwinbhatt.craft.orgmanager.service;


import com.ashwinbhatt.craft.orgmanager.exceptions.DbServiceException;
import com.ashwinbhatt.craft.orgmanager.models.*;
import com.ashwinbhatt.craft.orgmanager.repository.*;
import static com.ashwinbhatt.craft.orgmanager.utils.Utils.*;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbServices {

    private final OrgDetailRepository orgDetailRepository;
    private final UserRepository userRepository;
    private final OrgProductRegistryRepository orgProductRegistryRepository;
    private final ProductTypeRepository productTypeRepository;
    private final UserRoleRepository userRolesRepository;


    public DbServices(OrgDetailRepository orgDetailRepository, UserRepository userRepository, UserRoleRepository userRolesRepository, OrgProductRegistryRepository orgProductRegistryRepository, ProductTypeRepository productTypeRepository) {
        this.orgDetailRepository = orgDetailRepository;
        this.userRepository = userRepository;
        this.orgProductRegistryRepository = orgProductRegistryRepository;
        this.productTypeRepository = productTypeRepository;
        this.userRolesRepository = userRolesRepository;
    }

    // CRUD on OrgDetail entity
    public OrgDetail getOrgDetailById(@NonNull String orgId) throws DbServiceException {
        Optional<OrgDetail> orgDetailOptional = orgDetailRepository.findById(orgId);
        if(orgDetailOptional.isEmpty()) {
            throw new DbServiceException(String.format("Organisation with id %s don't exist", orgId));
        }
        return orgDetailOptional.get();
    }

    public OrgDetail getOrgDetailByLegalName(@NonNull String legalName) throws DbServiceException {
        Optional<OrgDetail> orgDetailOptional = orgDetailRepository.findByLegalName(legalName);
        if(orgDetailOptional.isEmpty()) {
            throw new DbServiceException(String.format("Organisation with id %s already exist", legalName));
        }
        return orgDetailOptional.get();
    }

    public boolean checkOrgByLegalNameExist(@NonNull String legalName) {
        Optional<OrgDetail> orgDetailOptional = orgDetailRepository.findByLegalName(legalName);
        return !orgDetailOptional.isEmpty();
    }

    public OrgDetail createOrdDetail(@NonNull OrgDetail orgDetail) throws DbServiceException {
        orgDetail.setOrgId(null);
        if(checkOrgByLegalNameExist(orgDetail.getLegalName())){
            throw new DbServiceException(String.format("Organisation with legalName  %s already exist", orgDetail.getLegalName()));
        }
        return orgDetailRepository.save(orgDetail);
    }

    public OrgDetail updateOrgDetail(OrgDetail orgDetail) throws DbServiceException {
        getOrgDetailById(orgDetail.getOrgId());
        getOrgDetailByLegalName(orgDetail.getLegalName());

        return orgDetailRepository.save(orgDetail);
    }

    // CRUB on user entity
    public boolean checkUserNameExist(@NonNull String userName) {
        return userRepository.findByUserName(userName) != null;
    }

    public User createUser(@NonNull UserRequest userRequest) throws DbServiceException {

        if(checkUserNameExist(userRequest.getUserName())) {
            throw new DbServiceException(String.format("Username %s already taken!", userRequest.getUserName()));
        }
        OrgDetail orgDetail = getOrgDetailById(userRequest.getOrgId());

        return userRepository.save(new User(null, orgDetail, userRequest.getUserName()));
    }

    public User readUser(@NonNull String userId) throws DbServiceException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new DbServiceException(String.format("User with id %s not found!", userId));
        }
        return userOptional.get();
    }

    public User updateUser(@NonNull String userId, @NonNull UserRequest userRequest) throws DbServiceException {
        User savedUser = readUser(userId);
        // do update on user except for userName
        return userRepository.save(savedUser);
    }

    public User deleteUser(@NonNull String userId) throws DbServiceException {
        User delUser = readUser(userId);
        userRepository.deleteById(userId);
        return delUser;
    }

    // CRUD on UserRole

    public UserRole createUserRole(@NonNull UserRoleRequest userRoleRequest) throws DbServiceException {
        validateProductType(userRoleRequest.getProductId());
        User user = readUser(userRoleRequest.getUserId());
        List<String> orgProducts = getAllProductIdForOrgId(user.getOrgDetail().getOrgId());
        if(!orgProducts.contains(userRoleRequest.getProductId())) {
            throw new DbServiceException(String.format("User %s is not registered for the product %s", userRoleRequest.getUserId(), userRoleRequest.getProductId()));
        }
        UserRole userRole = userRoleRequestToUserRole(userRoleRequest);
        return  userRolesRepository.save(userRole);
    }

    public UserRole readUserRole(@NonNull UserRolePk userRolePk) throws DbServiceException {
        Optional<UserRole> userRoleOptional = userRolesRepository.findById(userRolePk);
        if(userRoleOptional.isEmpty()) {
            throw new DbServiceException(String.format("Cannot find role for user %s for product %s", userRolePk.getUserId(), userRolePk.getProductId()));
        }
        return userRoleOptional.get();
    }

    public UserRole updateUserRole(@NonNull UserRoleRequest userRoleRequest) throws DbServiceException {
        UserRole userRole = userRoleRequestToUserRole(userRoleRequest);
        UserRole savedUserRole = readUserRole(userRole.getUserRolePk());
        //
        savedUserRole.setRole(userRoleRequest.getRole());
        return userRolesRepository.save(savedUserRole);
    }

    public UserRole deleteUserRole(@NonNull UserRolePk userRolePk) throws DbServiceException {
        UserRole userRole = readUserRole(userRolePk);
        userRolesRepository.deleteById(userRolePk);
        return userRole;
    }

    // We only require to validate ProductType operation on ProductType
    public void validateProductType(@NonNull String productId) throws DbServiceException {
        if(!productTypeRepository.existsById(productId)) {
            throw new DbServiceException(String.format("No product with id %s found!", productId));
        }
    }

    // CRUD on ORG product registry
    public OrgProductRegistry createOrgProductRegistry(@NonNull OrgProductRegistryRequest orgProductRegistryRequest) throws DbServiceException {
        validateProductType(orgProductRegistryRequest.getProductId());
        getOrgDetailById(orgProductRegistryRequest.getOrgId());
        OrgProductRegistry orgProductRegistry = orgProductRegistryRequestToOrgProductRegistry(orgProductRegistryRequest);
        if(orgProductRegistryRepository.existsById(orgProductRegistry.getOrgProductRegistryPk())){
            throw new DbServiceException(String.format("Org with id %s, is already registered for product id %s", orgProductRegistryRequest.getOrgId(), orgProductRegistryRequest.getProductId()));
        }
        return orgProductRegistryRepository.save(orgProductRegistry);
    }

    public OrgProductRegistry readOrgProductRegistry(@NonNull OrgProductRegistryPk orgProductRegistryPk) throws DbServiceException {
        Optional<OrgProductRegistry> orgProductRegistryOptional = orgProductRegistryRepository.findById(orgProductRegistryPk);
        if(orgProductRegistryOptional.isEmpty()) {
            throw new DbServiceException(String.format("Org %s, is not registered for product with id %s", orgProductRegistryPk.getOrgId(), orgProductRegistryPk.getProductId()));
        }
        return orgProductRegistryOptional.get();
    }

    public OrgProductRegistry updateOrgProductRegistry(@NonNull OrgProductRegistryRequest orgProductRegistryRequest) throws DbServiceException {
        OrgProductRegistry requestOrgProductRegistry = orgProductRegistryRequestToOrgProductRegistry(orgProductRegistryRequest);
        OrgProductRegistry savedOrgProductRegistry = readOrgProductRegistry(requestOrgProductRegistry.getOrgProductRegistryPk());
        // Do any update required
        return orgProductRegistryRepository.save(savedOrgProductRegistry);
    }

    public List<String> getAllProductIdForOrgId(@NonNull String orgId) throws DbServiceException {
        getOrgDetailById(orgId);
        List<OrgProductRegistry> orgProductRegistryList = orgProductRegistryRepository.findByOrgProductRegistryPkOrgId(orgId);
        return orgProductRegistryList.stream().map(x -> x.getOrgProductRegistryPk().getProductId()).toList();
    }

}
