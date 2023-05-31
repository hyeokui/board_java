package com.example.demo.domain.domain.comment.domain;

import com.example.demo.domain.domain.user.domain.User;
import com.example.demo.enums.common.RecommendStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecommendStatus recommendStatus;

    public CommentRecommend(User user, Comment comment, RecommendStatus recommendStatus) {
        this.user = user;
        this.comment = comment;
        this.recommendStatus = recommendStatus;
    }

    public void update() {
        this.recommendStatus = RecommendStatus.RECOMMEND;
    }

    public void cancel() {
        this.recommendStatus = RecommendStatus.UNRECOMMENDED;
    }
}
