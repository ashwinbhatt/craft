package com.ashwinbhatt.craft.orgmanager.utils;

import com.ashwinbhatt.craft.orgmanager.autoexpcache.RedisAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.cachetriggers.IAutoExpCacheTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class RedisExpireRecPoller extends Thread{

    private final static Logger logger = LoggerFactory.getLogger(RedisExpireRecPoller.class);

    private static RedisExpireRecPoller instance;
    private final RedisAutoExpCacheService redisService;
    private final IAutoExpCacheTrigger cacheTrigger;

    private final Long REDIS_EXP_POLL_SLEEP_DUR_MILSEC = 5 * 1000L;

    public static RedisExpireRecPoller getInstance(RedisAutoExpCacheService redisService, IAutoExpCacheTrigger autoExpCacheTrigger) {
        if(RedisExpireRecPoller.instance == null) {
            RedisExpireRecPoller.instance = new RedisExpireRecPoller(redisService, autoExpCacheTrigger);
        }
        return RedisExpireRecPoller.instance;
    }

    private RedisExpireRecPoller(RedisAutoExpCacheService redisService, IAutoExpCacheTrigger autoExpCacheTrigger) {
        this.redisService = redisService;
        this.cacheTrigger = autoExpCacheTrigger;
    }


    @Override
    public void run() {
        while(true) {
            Set<String> expiredKeys = redisService.expireOldRecordKeys();

            logger.debug("Polling for expired redis records!");

            logger.info("Polled record count "+ expiredKeys.size());
            // triggering redis trigger
            for(String expiredKey: expiredKeys) {
                cacheTrigger.onTriggerEvent(expiredKey);
            }

            try {
                Thread.sleep(REDIS_EXP_POLL_SLEEP_DUR_MILSEC);
            } catch (InterruptedException e) {
                logger.error("Interruption to Redis expiry record poller!!");
                throw new RuntimeException(e);
            }
        }
    }
}
