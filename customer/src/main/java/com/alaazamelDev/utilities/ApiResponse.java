package com.alaazamelDev.utilities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse {

    private String message;
    private Integer statusCode;
    private Object data;

    public static ApiResponse errorResponse(String message, Integer statusCode) {
        return new ApiResponse(message, statusCode, null);
    }

    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(message, 200, data);
    }

}
