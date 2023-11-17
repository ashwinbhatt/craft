package com.ashwinbhatt.craft.productinfo.configuration;

import com.ashwinbhatt.craft.orgmanager.autoexpcache.IAutoExpCacheService;
import com.ashwinbhatt.craft.orgmanager.autoexpcache.RedisAutoExpCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class SpringImportBeansConfiguration {

    @Value("${gossip.expcache}")
    private String autoExpCacheType;

    private final RedisTemplate<String, String> redisTemplate;
    private Long REDIS_REC_EXP_DUR_MILSEC;

    public SpringImportBeansConfiguration(RedisTemplate<String, String> redisTemplate, @Value("${gossip.redis.rec.exp.dur.sec}") Long redisExpTime) {
        this.redisTemplate = redisTemplate;
        this.REDIS_REC_EXP_DUR_MILSEC = redisExpTime;
    }

    @Bean
    public IAutoExpCacheService getAutoExpCacheServiceBean() {
        if("redis".equals(autoExpCacheType)) {
            return new RedisAutoExpCacheService(redisTemplate, REDIS_REC_EXP_DUR_MILSEC);
        }
        return null;
    }
}
