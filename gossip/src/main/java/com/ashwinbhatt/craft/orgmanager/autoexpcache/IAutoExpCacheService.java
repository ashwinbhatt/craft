package com.ashwinbhatt.craft.orgmanager.autoexpcache;

import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IAutoExpCacheService {

    protected static final Logger logger = LoggerFactory.getLogger(IAutoExpCacheService.class);

    public abstract void updateKey(String key) throws IAutoExpCacheServiceException;

    public abstract void addKey(String key) throws IAutoExpCacheServiceException;

    public abstract boolean checkKeyPresent(String key);

    public abstract boolean removeKeyIfPresent(String key);
}
