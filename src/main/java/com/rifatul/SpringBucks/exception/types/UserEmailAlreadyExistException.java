package com.rifatul.SpringBucks.exception.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.rifatul.SpringBucks.exception.ApiErrorCode.DUPLICATE_USER;
import static com.rifatul.SpringBucks.utils.GetErrorMessage.getErrorMessage;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserEmailAlreadyExistException extends RuntimeException {

    public UserEmailAlreadyExistException(String email) {
        super(String.format("The user %s email already exists.", email));
    }

    public UserEmailAlreadyExistException (String message, Throwable cause) {
        super(message, cause);
    }
}