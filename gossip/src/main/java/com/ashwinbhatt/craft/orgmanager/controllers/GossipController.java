package com.ashwinbhatt.craft.orgmanager.controllers;

import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import com.ashwinbhatt.craft.orgmanager.models.PingResponse;
import com.ashwinbhatt.craft.orgmanager.service.GossipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gossip")
public class GossipController {

    private final static Logger logger = LoggerFactory.getLogger(GossipController.class);

    private final GossipService gossipService;

    public GossipController(GossipService gossipService) {
        this.gossipService = gossipService;
    }

    @PostMapping("/ping")
    public PingResponse gossipPing(@RequestParam("serverId") String serverId) throws IAutoExpCacheServiceException {
        return this.gossipService.managePing(serverId);
    }

}
