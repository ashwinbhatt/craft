package com.ashwinbhatt.craft.orgmanager.controllers;

import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import com.ashwinbhatt.craft.orgmanager.exceptions.ServerStatusServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(IAutoExpCacheServiceException.class)
    public ResponseEntity<String> handleIAutoExpCacheServiceExpception(IAutoExpCacheServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerStatusServiceException.class)
    public ResponseEntity<String> handleServerStatusServiceException(ServerStatusServiceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
