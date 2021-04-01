package com.egy.clubtalk.exceptions.rooms;

import com.egy.clubtalk.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends ApiException {

    public RoomNotFoundException() {
        super("Room not found.");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
