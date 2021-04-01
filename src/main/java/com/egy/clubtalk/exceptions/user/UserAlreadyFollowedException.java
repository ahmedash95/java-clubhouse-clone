package com.egy.clubtalk.exceptions.user;

import com.egy.clubtalk.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyFollowedException extends ApiException {

    public UserAlreadyFollowedException() {}

    public UserAlreadyFollowedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "You already follow this user!";
    }
}
