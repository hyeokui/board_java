package com.example.demo.domain.domain.post.domain;

import com.example.demo.domain.domain.admin.domain.Admin;
import com.example.demo.domain.domain.board.domain.Board;
import com.example.demo.domain.domain.time.domain.BaseTime;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.enums.common.ReportStatus;
import com.example.demo.enums.post.PostStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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

    @PositiveOrZero
    private int recommendCount = 0;

    @PositiveOrZero
    private int reportCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    public Post(String title, String content, User user, Board board) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
        this.postStatus = PostStatus.ACTIVE;
    }

    public Post(String title, String content, Admin admin, Board board) {
        this.title = title;
        this.content = content;
        this.admin = admin;
        this.board = board;
        this.postStatus = PostStatus.ACTIVE;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void delete() {
        this.postStatus = PostStatus.DELETED;
    }

    public void increaseRecommendCount() {
        this.recommendCount++;
    }

    public void decreaseRecommendCount() {
        this.recommendCount--;
    }

}
