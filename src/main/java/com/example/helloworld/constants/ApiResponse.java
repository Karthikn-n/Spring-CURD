package com.example.helloworld.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ApiResponse<T> {
    private static final Logger log = LoggerFactory.getLogger(ApiResponse.class);
    private String status;
    private String message;
    private int code;
    private T data;
    public ApiResponse(String status, String message, int code) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = null; // Explicitly set data to null
    }
    public static <T> ApiResponse<T> success(String message, int code, T data) {
        return new ApiResponse<>("ok", message, code, data);
    }

    public static <T> ApiResponse<T> success(String message, int code) {
        return new ApiResponse<>("ok", message, code);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        // Ensure data is null for errors
        return new ApiResponse<>("error", message, code);
    }
}