package com.github.bingoohuang.springrest.boot.exception;

public class NotFoundException extends RestException {
    public NotFoundException(String message) {
        super(404, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(404, message, cause);
    }
}
