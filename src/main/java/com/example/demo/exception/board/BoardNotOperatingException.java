package com.example.demo.exception.board;

public class BoardNotOperatingException extends RuntimeException {

    public BoardNotOperatingException() {
        super("This board is not currently operating");
    }
}
