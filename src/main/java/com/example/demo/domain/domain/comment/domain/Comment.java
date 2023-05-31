package com.example.demo.domain.domain.comment.domain;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.time.domain.BaseTime;
import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.enums.comment.CommentStatus;
import com.example.demo.enums.common.RecommendStatus;
import com.example.demo.enums.common.ReportStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String content;

    @PositiveOrZero
    private int recommendCount = 0;

    @PositiveOrZero
    private int reportCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecommendStatus recommendStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;


    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }

    public void delete(){
        this.commentStatus = CommentStatus.DELETED;
    }

    public void increaseRecommendCount() {
        this.recommendCount++;
    }

    public void decreaseRecommendCount() {
        this.recommendCount--;
    }
}
