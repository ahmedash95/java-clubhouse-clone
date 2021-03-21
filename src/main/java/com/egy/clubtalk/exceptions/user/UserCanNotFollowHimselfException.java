package com.egy.clubtalk.exceptions.user;

import com.egy.clubtalk.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UserCanNotFollowHimselfException extends ApiException {
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getMessage() {
        return "User can not follow himself.";
    }
}
