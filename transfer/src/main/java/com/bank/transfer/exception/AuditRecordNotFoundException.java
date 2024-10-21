package com.bank.transfer.exception;

import lombok.Getter;

@Getter
public class AuditRecordNotFoundException extends RuntimeException {

    private final String location;

    public AuditRecordNotFoundException(String message, String location) {
        super(message);
        this.location = location;
    }

    public AuditRecordNotFoundException(String message, Throwable cause, String location) {
        super(message, cause);
        this.location = location;
    }
}
