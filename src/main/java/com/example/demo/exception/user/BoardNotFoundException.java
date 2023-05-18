package com.example.demo.exception.user;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException() {
        super("This board could not be found");
    }
}
