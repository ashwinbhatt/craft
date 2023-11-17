package com.ashwinbhatt.craft.orgmanager.service;

import com.ashwinbhatt.craft.orgmanager.autoexpcache.IAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import com.ashwinbhatt.craft.orgmanager.models.PingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GossipService {

    private final static Logger logger = LoggerFactory.getLogger(GossipService.class);

    private final IAutoExpCacheService autoExpCacheService;

    public GossipService(IAutoExpCacheService autoExpCacheService) {
        this.autoExpCacheService = autoExpCacheService;
    }

    public PingResponse managePing(String serverId) throws IAutoExpCacheServiceException {
        autoExpCacheService.updateKey(serverId);
        return new PingResponse("Success");
    }


}
