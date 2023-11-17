package com.ashwinbhatt.craft.commons.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private final String error;
    private final String message;

}
