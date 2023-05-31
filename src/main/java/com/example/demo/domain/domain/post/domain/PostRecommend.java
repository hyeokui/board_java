package com.example.demo.domain.domain.post.domain;

import com.example.demo.domain.domain.time.domain.BaseTime;
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
public class PostRecommend extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RecommendStatus recommendStatus;

    public PostRecommend(User user, Post post, RecommendStatus recommendStatus) {
        this.user = user;
        this.post = post;
        this.recommendStatus = recommendStatus;
    }

    public void update() {
        this.recommendStatus = RecommendStatus.RECOMMEND;
    }

    public void cancel() {
        this.recommendStatus = RecommendStatus.UNRECOMMENDED;
    }
}
