package com.archisacademy.report_generation_service.apiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
