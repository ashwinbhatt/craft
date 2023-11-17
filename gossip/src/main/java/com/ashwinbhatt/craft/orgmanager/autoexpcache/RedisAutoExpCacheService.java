package com.ashwinbhatt.craft.orgmanager.autoexpcache;

import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnProperty(name = "gossip.expcache", havingValue = "redis")
public class RedisAutoExpCacheService extends IAutoExpCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    private Long REDIS_REC_EXP_DUR_MILSEC;

    public RedisAutoExpCacheService(RedisTemplate<String, String> redisTemplate, @Value("${gossip.redis.rec.exp.dur.sec}") Long redisExpTime) {
        this.redisTemplate = redisTemplate;
        this.REDIS_REC_EXP_DUR_MILSEC = redisExpTime;
    }

    @Override
    public boolean checkKeyPresent(String key) {
        return Boolean.TRUE.equals(this.redisTemplate.hasKey(key));
    }

    @Override
    public boolean removeKeyIfPresent(String key) {
        if(checkKeyPresent(key)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    @Override
    public void updateKey(String key) throws IAutoExpCacheServiceException {
        if(!checkKeyPresent(key)) {
            logger.info(RedisAutoExpCacheService.class + String.format(" Redis key: %s expired. It is either expired or not registered. Please re-register!!", key));
            throw new IAutoExpCacheServiceException(String.format("Redis key: %s expired. It is either expired or not registered. Please re-register!!", key));
        }
        logger.debug(RedisAutoExpCacheService.class+ String.format(" Saving key %s to redis", key));
        this.redisTemplate.opsForValue().set(key, Long.toString(System.currentTimeMillis()));
    }

    @Override
    public void addKey(String key) throws IAutoExpCacheServiceException {
        if(checkKeyPresent(key)) {
            logger.info(RedisAutoExpCacheService.class + String.format(" Redis key: %s already exist.", key));
            throw new IAutoExpCacheServiceException(String.format("Redis key: %s already exist.", key));
        }
        this.redisTemplate.opsForValue().set(key, Long.toString(System.currentTimeMillis()));
    }

    public Set<String> expireOldRecordKeys() {
        Set<String> allKeys = redisTemplate.keys("*");
        Set<String> expiredKeys = new HashSet<>();
        for (String key : allKeys) {
            String enterTime = redisTemplate.opsForValue().get(key);
            if(enterTime == null) {
                continue;
            }
            Long keyTime = Long.parseLong(enterTime);
            if(System.currentTimeMillis() - keyTime > REDIS_REC_EXP_DUR_MILSEC * 1000) {
                redisTemplate.expire(key, 0, TimeUnit.SECONDS);
                expiredKeys.add(key);
            }
        }
        logger.debug(RedisAutoExpCacheService.class + String.format(" expired keys : %s", expiredKeys.size()));
        return expiredKeys;
    }
}
