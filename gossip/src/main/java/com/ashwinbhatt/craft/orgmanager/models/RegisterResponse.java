package com.ashwinbhatt.craft.orgmanager.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponse {
    private final String status;
    private final String failureMessage;
}
