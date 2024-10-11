package com.bank.profile.handlers.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Object entity, Long id) {
        super("Сущность " + entity + "+ с id: " + id + " не найдена.");
        log.error("Сущность {} с id: {} не найдена.", entity, id);
    }
}
