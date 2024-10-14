package com.bank.transfer.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String location;

    public EntityNotFoundException(String message, String location) {
        super(message);
        this.location = location;
    }

    public EntityNotFoundException(String message, Throwable cause, String location) {
        super(message, cause);
        this.location = location;
    }
}
