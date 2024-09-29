package com.alaazamelDev.utilities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse {

    private String message;
    private Integer statusCode;
    private Object data;

    public static HashMap<String, Object> errorResponse(String message, Integer statusCode) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", message);
        responseMap.put("statusCode", statusCode == null ? 400 : statusCode);
        return responseMap;
    }

    public static HashMap<String, Object> success(String message, Object data) {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", message);
        responseMap.put("statusCode", 200);
        responseMap.put("data", data);
        return responseMap;
    }

}
