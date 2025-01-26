package com.example.distancecounter.common.exception;

import com.example.distancecounter.common.bean.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger("API");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(final Exception ex) {
        logger.error("Exception: ", ex);
        return ResponseEntity.status(500).body(ApiResponse.fail("Internal Server Error"));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(final NoResourceFoundException ex) {
        return ResponseEntity.status(404).body(ApiResponse.fail(ex.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodNotAllowedException(final HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(400).body(ApiResponse.fail("Invalid HTTP method: " + ex.getMethod()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidInputException(final MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.status(400).body(ApiResponse.fail("Error - " + errorMsg));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(final CustomException ex) {
        String errorMsg = ex.getMessage();
        return ResponseEntity.status(ex.getCode()).body(ApiResponse.fail(errorMsg));
    }
}
