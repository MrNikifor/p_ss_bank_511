package com.bank.antifraud.exception;

import lombok.Getter;

@Getter
public class AuditRecordNotFoundException extends RuntimeException {

    private final String location;

    public AuditRecordNotFoundException(String message, String location) {
        super(message);
        this.location = location;
    }
}
