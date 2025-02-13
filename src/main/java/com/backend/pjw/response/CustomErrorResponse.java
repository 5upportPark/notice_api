package com.backend.pjw.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomErrorResponse {
    private int code;
    private String message;

    public static CustomErrorResponse newOne(HttpStatus status, String msg){
        return CustomErrorResponse.builder()
                .code(status.value())
                .message(msg)
                .build();
    }

    @Builder
    public CustomErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
