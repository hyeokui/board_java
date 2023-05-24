package com.example.demo.exception.board;

public class BoardNotFoundException extends RuntimeException {

    public BoardNotFoundException() {
        super("This board could not be found");
    }
}
