package com.example.demo.exception.admin;

public class DuplicateAdminIdException extends RuntimeException {

    public DuplicateAdminIdException(String errorMessage) {
        super(errorMessage);

    }
}
