package com.example.demo.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("This ID could not be found");
    }
}
