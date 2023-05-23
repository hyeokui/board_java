package com.example.demo.exception.post;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("This post could not be found");
    }
}
