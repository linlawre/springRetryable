package com.example.search.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class TooManyRequestException extends HttpServerErrorException {
    public TooManyRequestException(HttpStatus s, String message) {
        super(s, message);
    }
}
