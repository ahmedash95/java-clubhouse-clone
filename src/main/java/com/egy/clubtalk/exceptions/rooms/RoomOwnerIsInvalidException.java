package com.egy.clubtalk.exceptions.rooms;

import com.egy.clubtalk.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class RoomOwnerIsInvalidException extends ApiException {

    public RoomOwnerIsInvalidException() {
        super("Room has invalid owner id.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
