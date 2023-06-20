package com.example.lan_demo.exception;

import com.example.lan_demo.base.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> MyException(RuntimeException e) {
        if (e instanceof BadRequestException) {
            return new ResponseEntity<>(BaseResponse.error(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
        if(e instanceof ForbiddenException){
            return new ResponseEntity<>(BaseResponse.error(e.getMessage(), 403),HttpStatus.FORBIDDEN);
        }
        if(e instanceof UnauthorizedException){
            return new ResponseEntity<>(BaseResponse.error(e.getMessage(), 401), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(BaseResponse.error(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
