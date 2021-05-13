package com.egy.clubtalk.exceptions.user;

import com.egy.clubtalk.exceptions.ApiException;
import com.egy.clubtalk.exceptions.ApiValidationException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyTakenException extends ApiValidationException {

    public EmailAlreadyTakenException() {

    }

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

    @Override
    public String getFieldName() {
        return "email";
    }
}
