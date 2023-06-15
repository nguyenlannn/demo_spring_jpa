package com.example.lan_demo.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorCode;

    private boolean success;

    public static ResponseDto error(String message, Integer errorCode) {
        return ResponseDto.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message).build();
    }
}
