package com.bank.antifraud.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String location;

    public EntityNotFoundException(String message, String location) {
        super(message);
        this.location = location;
    }
}
