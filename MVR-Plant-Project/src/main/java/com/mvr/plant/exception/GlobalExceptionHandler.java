package com.mvr.plant.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    private ResponseEntity<?> silent() {
        return ResponseEntity.ok().body("");  // SSE requires silent no-error response
    }

    // ---------------------- SPECIFIC EXCEPTIONS ----------------------

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        if (isSseRequest()) return silent();

        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (isSseRequest()) return silent();

        String message = "Invalid value for parameter '" + ex.getName()
                + "'. Expected type: " + (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParameter(MissingServletRequestParameterException ex) {
        if (isSseRequest()) return silent();

        String msg = "Required parameter '" + ex.getParameterName()
                + "' is missing. Please provide this value.";

        return buildResponse(HttpStatus.BAD_REQUEST, msg);
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
        body.put("message", "Validation failed. Please check the entered details.");
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    // ---------------------- JSON & BODY ERRORS ----------------------

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleUnreadableJson(HttpMessageNotReadableException ex) {
        if (isSseRequest()) return silent();

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid or malformed JSON. Please check symbols like comma (,), colon (:), or missing values."
        );
    }


    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateParseError(DateTimeParseException ex) {
        if (isSseRequest()) return silent();

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "message", "Invalid date format. Please use yyyy-MM-dd.",
                        "received", ex.getParsedString()
                ));
    }


    // ---------------------- DATABASE ERRORS ----------------------

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (isSseRequest()) return silent();

        String root = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";
        String message = "Duplicate or invalid data. Please verify your input.";

        if (root.contains("PlantID"))
            message = "Plant ID already exists. Please use a different Plant ID.";

        else if (root.contains("vehicleChassisNo"))
            message = "Vehicle chassis number already exists. Each vehicle must have a unique chassis number.";

        else if (root.contains("employeeId"))
            message = "Employee ID already exists. Each employee must have a unique ID.";

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }


    // ---------------------- GENERAL FALLBACK ----------------------

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormat(NumberFormatException ex) {
        if (isSseRequest()) return silent();

        return buildResponse(HttpStatus.BAD_REQUEST,
                "Invalid numeric input. Please ensure IDs and numeric fields contain only numbers.");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        if (isSseRequest()) return silent();

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error occurred. Please try again or contact support."
        );
    }


    // ---------------------- COMMON RESPONSE BUILDER ----------------------

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}
