package com.rifatul.SpringBucks.exception;

import com.rifatul.SpringBucks.exception.types.UserEmailAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

//@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

//    @ExceptionHandler(value = {UserEmailAlreadyExistException.class})
    public ResponseEntity<Object> handleUserEmailAlreadyExistException(UserEmailAlreadyExistException e,
                                                                       WebRequest request) {
        ApiBaseException apiBaseException = new ApiBaseException(
                e.getMessage(), e, badRequest, ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiBaseException, badRequest);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        ApiBaseException apiBaseException = new ApiBaseException(
                e.getMessage(), e, badRequest, ZonedDateTime.now(ZoneId.of("Z"))
        );
        System.out.println("illegal access!!");
        return new ResponseEntity<>(apiBaseException, badRequest);
    }



}