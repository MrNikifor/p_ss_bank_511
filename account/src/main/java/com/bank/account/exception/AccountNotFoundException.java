package com.bank.account.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account with id " + id + " not found.");
        log.error("Счёт с таким ID: [{}] не найден", id);
    }
}
