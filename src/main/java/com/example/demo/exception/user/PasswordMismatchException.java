package com.example.demo.exception.user;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("passwords do not match");
    }
}
