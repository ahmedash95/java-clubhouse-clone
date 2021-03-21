package com.egy.clubtalk.exceptions.authentication;

import com.egy.clubtalk.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {

    public InvalidCredentialsException() {

    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public String getMessage() {
        return "Invalid credentials!";
    }
}
