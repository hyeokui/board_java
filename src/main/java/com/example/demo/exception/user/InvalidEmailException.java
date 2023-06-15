package com.example.demo.exception.user;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("Email format is incorrect");
    }
}
