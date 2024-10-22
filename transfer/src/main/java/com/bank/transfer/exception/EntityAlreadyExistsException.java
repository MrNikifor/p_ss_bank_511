package com.bank.transfer.exception;

import lombok.Getter;

@Getter
public class EntityAlreadyExistsException extends RuntimeException {

    private final String location;

    public EntityAlreadyExistsException(String message, String location) {
        super(message);
        this.location = location;
    }

    public EntityAlreadyExistsException(String message, Throwable cause, String location) {
        super(message, cause);
        this.location = location;
    }
}
