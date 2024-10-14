package com.bank.transfer.exception;

import lombok.Getter;

@Getter
public class RequiredFieldEmptyException extends RuntimeException {

    private final String location;

    public RequiredFieldEmptyException(String message, String location) {
        super(message);
        this.location = location;
    }

    public RequiredFieldEmptyException(String message, String location, Throwable cause) {
        super(message, cause);
        this.location = location;
    }
}
