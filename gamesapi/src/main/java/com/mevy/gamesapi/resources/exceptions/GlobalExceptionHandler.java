package com.mevy.gamesapi.resources.exceptions;

import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @Value("${server.error.include-exception}")
    private boolean printStackTrace;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> uncaughtExceptionHandler(Exception e, WebRequest request) {
        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Object> resourceNotFoundHandler(ResourceNotFound e, WebRequest request) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseIntegrityException.class)
    public ResponseEntity<Object> databaseIntegrityException(DatabaseIntegrityException e, WebRequest request) {
        return buildErrorResponse(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception e, HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(Instant.now(), status.value(), message);
        if (printStackTrace) {
            error.setStackTrace(ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception e, HttpStatus status) {
        return buildErrorResponse(e, status, e.getMessage());
    }
    
}
