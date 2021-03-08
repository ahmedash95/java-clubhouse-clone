package com.egy.clubhouse_clone.exceptions;

public abstract class ApiException extends RuntimeException implements ApiExceptionInterface {
    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
