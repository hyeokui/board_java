package com.example.demo.exception.comment;

public class CommentNotRecommendedException extends RuntimeException {

    public CommentNotRecommendedException() {
        super("Comment is not recommended");
    }
}
