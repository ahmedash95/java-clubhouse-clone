package com.egy.clubtalk.exceptions;

public abstract class ApiException extends RuntimeException implements ApiExceptionInterface {
    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
