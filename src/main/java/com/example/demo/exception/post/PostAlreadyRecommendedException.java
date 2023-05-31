package com.example.demo.exception.post;

public class PostAlreadyRecommendedException extends RuntimeException {

    public PostAlreadyRecommendedException() {
        super("The post has already been recommended");
    }
}
