package com.ashwinbhatt.craft.orgmanager.cachetriggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IAutoExpCacheTrigger {

    protected static final Logger logger = LoggerFactory.getLogger(IAutoExpCacheTrigger.class);

    public abstract void onTriggerEvent(String serverId);
}
