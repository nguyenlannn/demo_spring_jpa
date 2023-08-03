package com.example.lan_demo.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    private boolean success;

    public static BaseResponse error(String message) {
        return BaseResponse.builder()
                .success(false)
                .message(message).build();
    }

    public static BaseResponse error(String message, Integer errorCode) {
        return BaseResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message).build();
    }

    public static BaseResponse error(String message, Integer errorCode, Object data) {
        return BaseResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .data(data)
                .message(message).build();
    }

    public static BaseResponse error(Object data) {
        return BaseResponse.builder()
                .success(false)
//                .errorCode(errorCode)
                .data(data)
//                .message(message)
                .build();
    }

    public static BaseResponse success(String message) {
        return BaseResponse.builder()
                .success(true)
                .message(message)
                .build();
    }

    public static BaseResponse success(Object data) {
        return BaseResponse.builder()
                .success(true)
                .data(data)
                .build();
    }

    public static BaseResponse success(Object data, String message) {
        return BaseResponse.builder()
                .success(true)
                .data(data)
                .message(message)
                .build();
    }
}
