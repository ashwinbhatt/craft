package com.ashwinbhatt.craft.tsheets.web;

import com.ashwinbhatt.craft.commons.requests.AddressChangeRequest;
import com.ashwinbhatt.craft.commons.response.ErrorResponse;
import com.ashwinbhatt.craft.tsheets.exceptions.AppDbServiceException;
import com.ashwinbhatt.craft.tsheets.modules.UserTrack;
import com.ashwinbhatt.craft.tsheets.services.AppDbServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tsheets")
public class TSheetController {

    private final AppDbServices appDbServices;

    public TSheetController(AppDbServices appDbServices) {
        this.appDbServices = appDbServices;
    }

    @PostMapping("/usertrack/{userId}")
    public boolean updateUserTrack(@PathVariable String userId) throws AppDbServiceException {
        return appDbServices.userTrackUpdate(userId);
    }

    @GetMapping("/usertrack/userId")
    public UserTrack getUserTrack(@PathVariable String userId) throws AppDbServiceException {
        return appDbServices.getUserTrackInfo(userId);
    }

    @PostMapping("/org/validate")
    public Boolean validate(@RequestBody AddressChangeRequest addressChangeRequest) {
        return Boolean.FALSE;
    }


    @ExceptionHandler(AppDbServiceException.class)
    public ResponseEntity<ErrorResponse> handleAppDbServiceException(AppDbServiceException appDbServiceException) {
        ErrorResponse er  = new ErrorResponse("Failure", appDbServiceException.getMessage());
        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ResponseEntity<>(new ErrorResponse("Failure", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
