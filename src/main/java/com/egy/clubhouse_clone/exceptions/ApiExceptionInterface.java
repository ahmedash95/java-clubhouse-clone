package com.egy.clubhouse_clone.exceptions;

import org.springframework.http.HttpStatus;

public interface ApiExceptionInterface {
    HttpStatus getStatusCode();
    String getMessage();
}
