package com.egy.clubhouse_clone.exceptions.user;

import com.egy.clubhouse_clone.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyTakenException extends ApiException {

    public EmailAlreadyTakenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

    @Override
    public String getMessage() {
        return "The email is already taken!";
    }
}
