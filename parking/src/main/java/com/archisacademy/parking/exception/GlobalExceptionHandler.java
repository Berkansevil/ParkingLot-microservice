package com.archisacademy.parking.exception;

import com.archisacademy.parking.ApiResponse.ApiResponse;
import com.archisacademy.parking.exception.user.UserExistByEmailException;
import com.archisacademy.parking.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalExceptionHandler {

    // Error message template
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String error, Object message) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", error);
        errorDetails.put("message", message);
        return ResponseEntity.status(status).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Validation failed", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "An error occurred: " + e.getMessage()));
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleVehicleNotFound(VehicleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage()));
    }

    @ExceptionHandler(UserExistByEmailException.class)
    public ResponseEntity<ApiResponse<?>> handleUserExistByEmail(UserExistByEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, ex.getMessage()));
    }

    @ExceptionHandler(ParkingSpotNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleParkingSpotNotFound(ParkingSpotNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage()));
    }

}
