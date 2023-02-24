package com.kartasiewicz.comments;

import com.kartasiewicz.comments.exceptions.IllegalParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> handleWebClientRequestException(WebClientRequestException exception) {
        String errorMessage = "External service error.";
        logger.error("Error from WebClient - Method {}, Uri {}", exception.getMethod(), exception.getUri());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handleWebClientResponseException(WebClientResponseException exception) {
        logger.error("Error from WebClient - Status {}, Body {}", exception.getRawStatusCode(), exception.getResponseBodyAsString(), exception);

        return ResponseEntity.status(exception.getRawStatusCode()).body(exception.getResponseBodyAsString());
    }

    @ExceptionHandler(IllegalParameterException.class)
    public ResponseEntity<Object> handleIllegalParameterException(IllegalParameterException exception) {
        logger.warn("Validation error.");

        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        logger.error("Exception occurred", exception);

        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
