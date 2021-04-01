package com.egy.clubtalk.exceptions.rooms;

import com.egy.clubtalk.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyInvitedToRoomException  extends ApiException {

    public UserAlreadyInvitedToRoomException() {
        super("User is already invited.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}