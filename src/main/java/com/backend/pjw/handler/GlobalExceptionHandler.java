package com.backend.pjw.handler;

import com.backend.pjw.response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CustomErrorResponse> illegalArgumentHandle(MethodArgumentNotValidException ex){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        CustomErrorResponse er = CustomErrorResponse.newOne(httpStatus, "잘못된 요청입니다.");
        return ResponseEntity.status(httpStatus).body(er);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<CustomErrorResponse> defaultHandle(Exception ex){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        CustomErrorResponse er = CustomErrorResponse.newOne(httpStatus, ex.getMessage());
        return ResponseEntity.status(httpStatus).body(er);
    }
}
