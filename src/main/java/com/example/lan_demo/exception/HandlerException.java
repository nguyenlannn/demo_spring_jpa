package com.example.lan_demo.exception;

import com.example.lan_demo.dto.res.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class HandlerException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto> MyException(RuntimeException e) {
        if (e instanceof BadRequestException) {
            return new ResponseEntity<>(ResponseDto.error(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
        }
        if(e instanceof ForbiddenException){
            return new ResponseEntity<>(ResponseDto.error(e.getMessage(), 403),HttpStatus.FORBIDDEN);
        }
        if(e instanceof UnauthorizedException){
            return new ResponseEntity<>(ResponseDto.error(e.getMessage(), 401), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(ResponseDto.error(e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
