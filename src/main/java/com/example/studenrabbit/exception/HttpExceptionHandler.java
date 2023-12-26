package com.example.studenrabbit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorInfo> handleNotFoundException(StudentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getStudentError().getMessage()));
    }
}
