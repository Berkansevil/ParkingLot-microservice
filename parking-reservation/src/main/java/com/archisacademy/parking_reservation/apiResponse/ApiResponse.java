package com.archisacademy.parking_reservation.apiResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;

    public ApiResponse(Boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
