package com.example.demo.domain.domain.post.domain;

import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.time.domain.BaseTime;
import com.example.demo.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int views;

    @ManyToOne
    private User user;

    @ManyToOne
    private Board board;

    protected Post() {
    }

    public Post(String title, String content, User user, Board board) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
    }
}
