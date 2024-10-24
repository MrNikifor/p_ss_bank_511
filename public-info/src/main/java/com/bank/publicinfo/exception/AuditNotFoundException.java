package com.bank.publicinfo.exception;


public class AuditNotFoundException extends RuntimeException {

    public AuditNotFoundException(String message) {
        super(message);
    }

    public AuditNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
