package com.mevy.gamesapi.resources.exceptions;

import java.io.IOException;
import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mevy.gamesapi.services.exceptions.DatabaseIntegrityException;
import com.mevy.gamesapi.services.exceptions.ResourceNotFound;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler{

    @Value("${server.error.include-exception:false}")
    private boolean printStackTrace;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                Instant.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details.");
        
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

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

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        Integer status = HttpStatus.FORBIDDEN.value();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("Application/json");
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(), status, "Email or Password are invalid. ");
        response.getWriter().append(errorResponse.toJson());
    }
    
}
