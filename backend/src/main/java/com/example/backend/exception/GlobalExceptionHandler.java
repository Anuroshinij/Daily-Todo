package com.example.backend.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
                        ResourceNotFoundException ex) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .message(ex.getMessage())
                                .build();

                return new ResponseEntity<>(
                                error,
                                HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationException(
                        MethodArgumentNotValidException ex) {

                Map<String, String> errors = new HashMap<>();

                ex.getBindingResult()
                                .getFieldErrors()
                                .forEach(error -> errors.put(
                                                error.getField(),
                                                error.getDefaultMessage()));

                return new ResponseEntity<>(
                                errors,
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(UserNameAlreadyExistsException.class)
        public ResponseEntity<ErrorResponse> handleUserNameAlreadyExistsException(UserNameAlreadyExistsException ex) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(ex.getMessage())
                                .build();

                return new ResponseEntity<>(
                                error,
                                HttpStatus.BAD_REQUEST);

        }

        @ExceptionHandler(InvalidCredentialsException.class)
        public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(
                        InvalidCredentialsException ex) {

                ErrorResponse error = ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .message(ex.getMessage())
                                .build();

                return new ResponseEntity<>(
                                error,
                                HttpStatus.UNAUTHORIZED);
        }
}