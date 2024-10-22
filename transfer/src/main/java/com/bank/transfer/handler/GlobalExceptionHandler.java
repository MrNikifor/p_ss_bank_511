package com.bank.transfer.handler;

import com.bank.transfer.exception.AuditRecordNotFoundException;
import com.bank.transfer.exception.EntityAlreadyExistsException;
import com.bank.transfer.exception.EntityIdNotFoundException;
import com.bank.transfer.exception.EntityNotFoundException;
import com.bank.transfer.exception.RequiredFieldEmptyException;
import com.bank.transfer.exception.UniqueFieldEmptyException;
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

    @ExceptionHandler(RequiredFieldEmptyException.class)
    public ResponseEntity<String> requiredFieldEmptyException(RequiredFieldEmptyException ex) {

        log.error("Required Field Empty: {} - {}", ex.getLocation(), ex.getMessage(), ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueFieldEmptyException.class)
    public ResponseEntity<String> uniqueFieldEmptyException(UniqueFieldEmptyException ex) {

        log.error("Unique Field Empty: {} - {}", ex.getLocation(), ex.getMessage(), ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> entityAlreadyExistsException(EntityAlreadyExistsException ex) {

        log.error("Entity already exists: {} - {}", ex.getLocation(), ex.getMessage(), ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityIdNotFoundException.class)
    public ResponseEntity<String> entityIdNotFoundException(EntityIdNotFoundException ex) {

        log.error("Field ID not found: {} - {}", ex.getLocation(), ex.getMessage(), ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuditRecordNotFoundException.class)
    public ResponseEntity<String> auditRecordNotFoundException(AuditRecordNotFoundException ex) {

        log.error("Audit Record Not Found: {} - {}", ex.getLocation(), ex.getMessage(), ex);

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {

        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
