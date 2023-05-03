package com.rifatul.SpringBucks.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    record ErrorDTO (String message, String status, String time) {}

    @ExceptionHandler(ProductAlreadyOnCartException.class)
    public ResponseEntity<ErrorDTO> generateNotFoundException(ProductAlreadyOnCartException ex) {
        ErrorDTO errorDTO = new ErrorDTO(ex.getMessage(), String.valueOf(ex.getMessage()), new Date().toString());
        return null;
//        return new ResponseEntity<ErrorDTO>(errorDTO, ex.getMessage().toString(), "");
    }

}
