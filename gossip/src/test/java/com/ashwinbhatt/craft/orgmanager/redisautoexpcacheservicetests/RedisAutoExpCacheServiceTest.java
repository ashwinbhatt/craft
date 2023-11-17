package com.ashwinbhatt.craft.orgmanager.redisautoexpcacheservicetests;

import com.ashwinbhatt.craft.orgmanager.autoexpcache.RedisAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.Set;


@SpringBootTest(classes = {Object.class})
public class RedisAutoExpCacheServiceTest {

    @Value("${gossip.redis.rec.exp.dur.sec}")
    private Long REDIS_REC_EXP_DUR_MILSEC;
    private RedisTemplate<String, String> redisTemplate;
    private RedisAutoExpCacheService redisAutoExpCacheService;

    @BeforeEach
    public void setup() {
        redisTemplate = new MockRedisTemplate<>();
        ((MockRedisTemplate) redisTemplate).addKey("1", Long.toString(System.currentTimeMillis()));
        redisAutoExpCacheService = new RedisAutoExpCacheService(redisTemplate, REDIS_REC_EXP_DUR_MILSEC);
    }


    @Test
    public void test() {

        boolean keyPresent = redisAutoExpCacheService.checkKeyPresent("1");
        Assert.isTrue(keyPresent, String.format("Test failed as key %s should be present, but test indicate it is not!", "1"));

        try {
            redisAutoExpCacheService.updateKey("1");
        } catch (IAutoExpCacheServiceException e) {
            Assert.isTrue(Boolean.FALSE, "Update Key Test fail error: "+ e.getMessage());
        }

        // expire key
        try {
            Thread.sleep((REDIS_REC_EXP_DUR_MILSEC + 1)* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Set<String> expiredRecs = redisAutoExpCacheService.expireOldRecordKeys();

        Assert.isTrue(expiredRecs.contains("1"), "Record '1' not found in expired record list");

    }
}
