package com.ashwinbhatt.craft.orgmanager.service;


import com.ashwinbhatt.craft.orgmanager.exceptions.ServerStatusServiceException;
import com.ashwinbhatt.craft.orgmanager.models.ServerStatus;
import com.ashwinbhatt.craft.orgmanager.models.ServerStatusEnum;
import com.ashwinbhatt.craft.orgmanager.repositories.ServerStatusRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServerStatusService {

    private final static Logger logger = LoggerFactory.getLogger(ServerStatusService.class);


    private final ServerStatusRepository serverStatusRepository;

    public ServerStatusService(ServerStatusRepository serverStatusRepository) {
        this.serverStatusRepository = serverStatusRepository;
    }

    public ServerStatus getServerStatus(String serverId) throws ServerStatusServiceException {
        Optional<ServerStatus> optionalServerStatus = serverStatusRepository.findById(serverId);
        if(optionalServerStatus.isEmpty()) {
            logger.info(String.format("Server with id: %s dosn't exist", serverId));
            throw new ServerStatusServiceException(String.format("Server with id: %s dosn't exist", serverId));
        }
        return optionalServerStatus.get();
    }

    public ServerStatus changeServerStatus(String serverId, ServerStatusEnum serverStatusEnum) throws ServerStatusServiceException {
        ServerStatus savedServerStatus = getServerStatus(serverId);
        savedServerStatus.setServerStatus(serverStatusEnum);
        return serverStatusRepository.save(savedServerStatus);
    }

    public ServerStatus createServerStatus(String serverId, ServerStatusEnum serverStatusEnum) throws ServerStatusServiceException {
        try {
            getServerStatus(serverId);
            // thow exception as we expect no record
            logger.info(String.format("Cannot create server status for id: %s, status already exsits!!"));
            throw new ServerStatusServiceException(String.format("Cannot create server status for id: %s, status already exsits!!"));
        } catch (ServerStatusServiceException e) {
            // Create record
            ServerStatus serverStatus = new ServerStatus(serverId, serverStatusEnum);
            return serverStatusRepository.save(serverStatus);
        }
    }

}
