package com.thiago.testapplication.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handle(RuntimeException ex) {
        log.error(ex.getClass().getName(), ex);

        var message = ResponseException.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(ex.getMessage())
            .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handle(BindException ex) {
        log.error(ex.getClass().getName(), ex);

        var errors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> String.format("'%s' %s", e.getField(), e.getDefaultMessage()))
            .collect(Collectors.toList());

        var message = ResponseException.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(String.join("\n", errors))
            .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handle(ResponseStatusException ex) {
        log.error(ex.getClass().getName(), ex);

        var message = ResponseException.builder()
            .status(HttpStatus.valueOf(ex.getStatusCode().value()))
            .message(ex.getMessage())
            .build();

        return new ResponseEntity<>(message, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error(ex.getClass().getName(), ex);

        var message = ResponseException.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message("Internal Server Error")
            .build();

        return new ResponseEntity<>(message, message.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
        log.error(ex.getClass().getName(), ex);

        var message = ResponseException.builder()
            .status(HttpStatus.FORBIDDEN)
            .message("Access denied")
            .build();

        return new ResponseEntity<>(message, message.getStatus());
    }
}
