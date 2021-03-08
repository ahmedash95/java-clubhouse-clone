package com.egy.clubhouse_clone.exceptions.user;

import com.egy.clubhouse_clone.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException() {}
    
    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage() {
        return "User is not found!";
    }
}

