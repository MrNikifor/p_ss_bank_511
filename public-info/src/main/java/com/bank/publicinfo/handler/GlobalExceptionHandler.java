package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс GlobalExceptionHandler отвечает за обработку ошибок в приложении.
 *
 * Этот класс используется для централизованной обработки исключений, которые могут возникать
 * в контроллерах. Он позволяет возвращать понятные сообщения об ошибках и соответствующие
 * коды состояния HTTP.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ошибки валидации данных.
     *
     * @param ex Исключение, связанное с невалидными данными.
     * @return Ответ с картой ошибок и статусом BAD_REQUEST (400).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает ошибки, когда ресурс не найден.
     *
     * @param ex Исключение, связанное с отсутствием ресурса.
     * @return Ответ с сообщением об ошибке и статусом NOT_FOUND (404).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает все остальные ошибки.
     *
     * @param ex Общее исключение.
     * @return Ответ с сообщением об ошибке и статусом INTERNAL_SERVER_ERROR (500).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

