package com.ashwinbhatt.craft.orgmanager.cachetriggers;

import com.ashwinbhatt.craft.orgmanager.exceptions.ServerStatusServiceException;
import com.ashwinbhatt.craft.orgmanager.models.ServerStatusEnum;
import com.ashwinbhatt.craft.orgmanager.service.ServerStatusService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "gossip.expcache", havingValue = "redis")
public class RedisExpRecPollTrigger extends IAutoExpCacheTrigger{

    private final ServerStatusService serverStatusService;

    public RedisExpRecPollTrigger(ServerStatusService serverStatusService) {
        this.serverStatusService = serverStatusService;
    }

    @Override
    public void onTriggerEvent(String serverId) {
        // mark the server status as down
        try {
            logger.debug(RedisExpRecPollTrigger.class + String.format(" trigger for key %s", serverId));
            this.serverStatusService.changeServerStatus(serverId, ServerStatusEnum.down);
        } catch (ServerStatusServiceException e) {
            System.out.println("Unable to mark server status down in DB error: "+e.getMessage());
        }
    }
}
