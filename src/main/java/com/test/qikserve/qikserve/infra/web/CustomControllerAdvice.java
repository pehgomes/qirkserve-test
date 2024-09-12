package com.test.qikserve.qikserve.infra.web;

import com.test.qikserve.qikserve.infra.exception.IntegrationException;
import com.test.qikserve.qikserve.infra.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        return toApiErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ApiError> handleIntegrationException(IntegrationException ex) {
        return toApiErrorResponse(ex, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private static ResponseEntity<ApiError> toApiErrorResponse(RuntimeException ex, HttpStatus status) {
        return new ResponseEntity<>(new ApiError(status.value(), List.of(ex.getMessage())), status);
    }

    public record ApiError(Integer code, List<String> errors) {}
}
