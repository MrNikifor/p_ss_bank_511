package com.bank.antifraud.hendler;

import com.bank.antifraud.exception.AuditRecordNotFoundException;
import com.bank.antifraud.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException ex) {

        log.error("Entity not Found: {} - {}", ex.getLocation(), ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuditRecordNotFoundException.class)
    public ResponseEntity<String> auditRecordNotFoundException(AuditRecordNotFoundException ex) {

        log.error("Audit Record Not Found: {} - {}", ex.getLocation(), ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
