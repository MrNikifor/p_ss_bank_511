package com.bank.transfer.exception;

import lombok.Getter;

@Getter
public class UniqueFieldEmptyException extends RuntimeException {

    private final String location;

    public UniqueFieldEmptyException(String message, String location) {
        super(message);
        this.location = location;
    }

    public UniqueFieldEmptyException(String message, String location, Throwable cause) {
        super(message, cause);
        this.location = location;
    }
}
