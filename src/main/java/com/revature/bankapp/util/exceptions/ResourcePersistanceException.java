package com.revature.bankapp.util.exceptions;

public class ResourcePersistanceException extends RuntimeException {

    public ResourcePersistanceException(String message) {
        super(message);  //calls the parent constructor of the form RuntimeException(String message) which constructs a new runtime exception with the specified detail message.
    }
}