package com.egy.clubtalk.exceptions;

public abstract class ApiValidationException extends ApiException implements ApiValidationExceptionInterface {
    public ApiValidationException() {
    }

    public ApiValidationException(String message) {
        super(message);
    }
}
