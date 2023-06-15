package com.example.demo.exception.user;

public class InvalidPhoneException extends RuntimeException {

    public InvalidPhoneException() {
        super("Please enter your phone number in the format 000-0000-0000");
    }
}
