package com.mvr.plant.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔥 Detect SSE requests so logs can be suppressed
    private boolean isSseRequest() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) return false;

        HttpServletRequest req = attrs.getRequest();
        String accept = req.getHeader("Accept");
        String contentType = req.getContentType();

        return (accept != null && accept.contains("text/event-stream"))
                || (contentType != null && contentType.contains("text/event-stream"));
    }


    // 🔥 Suppress logs for SSE failures
    private ResponseEntity<?> silent() {
        return ResponseEntity.ok().body(""); // empty SSE-safe response
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        if (isSseRequest()) return silent();

        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (isSseRequest()) return silent();

        return buildResponse(HttpStatus.BAD_REQUEST,
                "Invalid value for parameter: " + ex.getName());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        if (isSseRequest()) return silent();

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (isSseRequest()) return silent();

        String message = "Duplicate entry. Plant ID already exists.";
        if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("duplicate")) {
            message = ex.getRootCause().getMessage();
        }

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateParseError(DateTimeParseException ex) {
        if (isSseRequest()) return silent();

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "error", "Invalid date format. Please use yyyy-MM-dd.",
                        "details", ex.getParsedString()
                ));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        if (isSseRequest()) return silent();

        // 🔇 remove logs: do NOT print stacktrace
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong on the server");
    }


    // Utility method for normal JSON API errors
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}
