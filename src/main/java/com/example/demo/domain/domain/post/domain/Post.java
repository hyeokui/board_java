package com.example.demo.domain.domain.post.domain;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.time.domain.BaseTime;
import com.example.demo.domain.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private PostRecommend postRecommend;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private PostReport postReport;

    public Post(String title, String content, User user, Board board) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
    }

    public Post(String title, String content, Admin admin, Board board) {
        this.title = title;
        this.content = content;
        this.admin = admin;
        this.board = board;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
