package com.example.gamedetails.models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
    private String devErrorMessage;
    private Map<String, Object> additionalData = new HashMap<>();
}