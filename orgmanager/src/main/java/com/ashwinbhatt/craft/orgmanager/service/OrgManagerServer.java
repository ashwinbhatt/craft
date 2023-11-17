package com.ashwinbhatt.craft.orgmanager.service;

import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import com.ashwinbhatt.craft.orgmanager.exceptions.DbServiceException;
import com.ashwinbhatt.craft.orgmanager.models.OrgDetail;
import com.ashwinbhatt.craft.orgmanager.productvalidator.SingleValidator;
import com.ashwinbhatt.craft.orgmanager.utils.Utils;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class OrgManagerServer {

    private final DbServices dbServices;
    private final int threadPoolSize = 2;

    private final RestTemplate restTemplate;


    public OrgManagerServer(DbServices dbServices, RestTemplate restTemplate) {
        this.dbServices = dbServices;
        this.restTemplate = restTemplate;
    }

    public OrgDetail updateOrgAddress(@NotNull AddressChangeRequest addressChangeRequest) throws DbServiceException, InterruptedException, ExecutionException {
        OrgDetail orgDetail = dbServices.getOrgDetailById(addressChangeRequest.getOrgId());

        List<String> productIds = dbServices.getAllProductIdForOrgId(orgDetail.getOrgId());

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        List<Future<Boolean>> futureList = new ArrayList<>();


        for(String productId: productIds) {
            SingleValidator singleValidator = new SingleValidator(Utils.getUrl(productId), addressChangeRequest, restTemplate);
            futureList.add(executorService.submit(singleValidator));
        }

        if(executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
        boolean canUpdate = true;
        for(Future<Boolean> future : futureList) {
            if(!future.get()) {
                canUpdate = false;
                break;
            }
        }

        if(canUpdate) {
            orgDetail.setLegalAddress(addressChangeRequest.getNewAddress());
            orgDetail = dbServices.updateOrgDetail(orgDetail);
        }
        return orgDetail;
    }
}
