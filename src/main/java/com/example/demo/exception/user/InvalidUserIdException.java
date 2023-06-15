package com.example.demo.exception.user;

public class InvalidUserIdException extends RuntimeException {

    public InvalidUserIdException() {
        super("ID can be used in English and numbers, and must be between 6 and 12 digits");
    }
}
