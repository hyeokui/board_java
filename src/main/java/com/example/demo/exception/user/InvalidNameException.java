package com.example.demo.exception.user;

public class InvalidNameException extends RuntimeException {

    public InvalidNameException() {
        super("The name must be at least 2 and no more than 10 digits, excluding special characters");
    }
}
