package com.revature.bankapp.util.exceptions;

public class InvalidUserInputException extends RuntimeException { // unchecked exception
    public InvalidUserInputException(String message) {
        super(message); //calls the parent constructor of the form RuntimeException(String message) which constructs a new runtime exception with the specified detail message.
    }
}