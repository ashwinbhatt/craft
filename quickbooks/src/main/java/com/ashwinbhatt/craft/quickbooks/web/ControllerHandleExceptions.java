package com.ashwinbhatt.craft.quickbooks.web;

import com.ashwinbhatt.craft.commons.response.ErrorResponse;
import com.ashwinbhatt.craft.quickbooks.exceptions.AppDBServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandleExceptions {

    @ExceptionHandler(AppDBServiceException.class)
    public ResponseEntity<ErrorResponse> handleAppDbServiceException(AppDBServiceException appDBServiceException) {
        ErrorResponse errorMessage = new ErrorResponse("FAILURE", appDBServiceException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String fieldName = fieldError.getField();
        String detailedMessage = String.format("Field %s is mandatory!", fieldName);
        ErrorResponse errorResponse = new ErrorResponse("Failure", detailedMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(Exception exception) {
        ErrorResponse errorMessage = new ErrorResponse("FAILURE", exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
