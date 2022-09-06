package com.example.search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Custom_handler {

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<?> tooManyRequestHandler() {
        System.out.println("In exception");
        return new ResponseEntity<>("What am I doing", HttpStatus.TOO_MANY_REQUESTS);
    }
}
