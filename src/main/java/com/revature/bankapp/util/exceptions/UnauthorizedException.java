package com.revature.bankapp.util.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);//calls the parent constructor of the form RuntimeException(String message) which constructs a new runtime exception with the specified detail message.
    }
}