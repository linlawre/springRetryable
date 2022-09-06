package com.example.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class TooManyRequestException extends HttpClientErrorException {
    public TooManyRequestException(HttpStatus s, String message) {
        super(s, message);
    }
}
