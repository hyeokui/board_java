package com.example.demo.exception.comment;

public class CommentAlreadyRecommendedException extends RuntimeException{

    public CommentAlreadyRecommendedException(){
        super("The comment has already been recommended");
    }
}
