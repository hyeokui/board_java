package com.example.demo.exception.admin;

public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException() {
        super("This admin could not be found");
    }
}
