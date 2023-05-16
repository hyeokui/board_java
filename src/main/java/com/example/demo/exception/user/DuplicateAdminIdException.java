package com.example.demo.exception.user;

public class DuplicateAdminIdException extends RuntimeException {

    public DuplicateAdminIdException(String errorMessage) {
        super(errorMessage);

    }
}
