package com.ashwinbhatt.craft.orgmanager.controller;

import com.ashwinbhatt.craft.commons.response.ErrorResponse;
import com.ashwinbhatt.craft.orgmanager.exceptions.DbServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String fieldName = fieldError.getField();
        String detailedMessage = String.format("Field %s is mandatory!", fieldName);
        ErrorResponse errorResponse = new ErrorResponse("Failure", detailedMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DbServiceException.class)
    public ResponseEntity<ErrorResponse> handleDBServicesExceptions(DbServiceException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Failure", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNPE(NullPointerException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Failure", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
