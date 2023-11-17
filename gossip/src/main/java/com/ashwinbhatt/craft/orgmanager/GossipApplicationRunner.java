package com.ashwinbhatt.craft.orgmanager;

import com.ashwinbhatt.craft.orgmanager.autoexpcache.IAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.autoexpcache.RedisAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.cachetriggers.IAutoExpCacheTrigger;
import com.ashwinbhatt.craft.orgmanager.utils.RedisExpireRecPoller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GossipApplicationRunner implements ApplicationRunner {

    private final IAutoExpCacheService autoExpCacheService;
    private final IAutoExpCacheTrigger cacheTrigger;

    @Value("${gossip.expcache}")
    private String autoExpCacheType;

    public GossipApplicationRunner(RedisAutoExpCacheService autoExpCacheService, IAutoExpCacheTrigger autoExpCacheTrigger) {
        this.autoExpCacheService = autoExpCacheService;
        this.cacheTrigger = autoExpCacheTrigger;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if("redis".equals(autoExpCacheType)) {
            // start RedisExpiredRecPoller thread.
            RedisExpireRecPoller.getInstance((RedisAutoExpCacheService) autoExpCacheService, cacheTrigger).start();
        }
    }
}
