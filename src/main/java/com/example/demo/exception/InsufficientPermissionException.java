package com.example.demo.exception;

public class InsufficientPermissionException extends RuntimeException {

    public InsufficientPermissionException() {
        super("Insufficient permission to perform the requested operation.");
    }
}
