package com.origin.test.urlshortener.controller;

import com.origin.test.urlshortener.exceptions.InvalidShortenURLCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(InvalidShortenURLCodeException.class)
    public ResponseEntity<Object> handleInvalidShortCodeException(InvalidShortenURLCodeException ex) {
        log.error("Exception occurred due to invalid short code", ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Exception occurred due to invalid input", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
