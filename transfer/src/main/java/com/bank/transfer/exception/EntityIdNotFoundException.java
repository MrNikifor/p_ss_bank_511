package com.bank.transfer.exception;

import lombok.Getter;

@Getter
public class EntityIdNotFoundException extends RuntimeException {

    private final String location;

    public EntityIdNotFoundException(String message, String location) {
        super(message);
        this.location = location;
    }

    public EntityIdNotFoundException(String message, Throwable cause, String location) {
        super(message, cause);
        this.location = location;
    }
}
