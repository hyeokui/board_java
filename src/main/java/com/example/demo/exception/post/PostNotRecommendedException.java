package com.example.demo.exception.post;

public class PostNotRecommendedException extends RuntimeException {

    public PostNotRecommendedException() {
        super("The post is not in the recommended state");
    }
}
