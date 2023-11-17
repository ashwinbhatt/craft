package com.ashwinbhatt.craft.payrolls.web;


import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import com.ashwinbhatt.craft.commons.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payrolls")
public class PayrollAppController {

    @PostMapping("/org/validate")
    public Boolean validate(@RequestBody AddressChangeRequest addressChangeRequest) {
        return Boolean.TRUE;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("FAILURE", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
