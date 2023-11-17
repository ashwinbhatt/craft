package com.ashwinbhatt.craft.productinfo.controllers;

import com.ashwinbhatt.craft.orgmanager.exceptions.IAutoExpCacheServiceException;
import com.ashwinbhatt.craft.productinfo.exceptions.DBServicesExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IAutoExpCacheServiceException.class)
    public ResponseEntity<ErrorResponse> handleIAutoExpCacheServiceExpception(IAutoExpCacheServiceException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Failure", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DBServicesExceptions.class)
    public ResponseEntity<ErrorResponse> handleDBServicesExceptions(DBServicesExceptions exception) {
        ErrorResponse errorResponse = new ErrorResponse("Failure", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String fieldName = fieldError.getField();
        String detailedMessage = String.format("Field %s is mandatory!", fieldName);
        ErrorResponse errorResponse = new ErrorResponse("Failure", detailedMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}