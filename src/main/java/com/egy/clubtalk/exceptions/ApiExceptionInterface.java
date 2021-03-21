package com.egy.clubtalk.exceptions;

import org.springframework.http.HttpStatus;

public interface ApiExceptionInterface {
    HttpStatus getStatusCode();
    String getMessage();
}
