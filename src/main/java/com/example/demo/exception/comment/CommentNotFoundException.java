package com.example.demo.exception.comment;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        super("Comment not found");
    }
}
