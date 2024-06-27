package com.mevy.gamesapi.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

    @JsonFormat(
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        timezone = "UTC",
        shape = JsonFormat.Shape.STRING
    )
    private final Instant timestamp;
    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationError> errors;

    @Getter
    @Setter
    @RequiredArgsConstructor
    private static class ValidationError {
        private final String field;
        private final String message;
    }

    public void addValidationError (String field, String message) {
        if (Objects.isNull(errors)) {
            this.errors = new ArrayList<>();
        }

        this.errors.add(new ValidationError(field, message));
    }

    public String toJson() {
        return String.format("{\"status\" : %d,\n\"message\" : \"%s\"}", status, message);
    }
    
}