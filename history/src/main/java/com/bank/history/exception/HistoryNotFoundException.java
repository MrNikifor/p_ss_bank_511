package com.bank.history.exception;

public class HistoryNotFoundException extends RuntimeException {

    public HistoryNotFoundException(Long id) {
        super("History not found with id: " + id);
    }
}
