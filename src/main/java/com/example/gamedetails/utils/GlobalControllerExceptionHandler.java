package com.example.gamedetails.utils;

import com.example.gamedetails.models.ErrorDetails;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorDetails> handleConflict(RuntimeException ex) {
        var error = new ErrorDetails();
        error.setErrorCode(HttpStatus.NOT_FOUND.toString());
        error.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}