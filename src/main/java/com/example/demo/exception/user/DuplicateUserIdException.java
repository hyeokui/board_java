package com.example.demo.exception.user;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(String errorMessage) {
        super(errorMessage);

    }
}
