package com.example.demo.domain.domain.comment.domain;

import com.example.demo.domain.domain.post.domain.Post;
import com.example.demo.domain.domain.time.domain.BaseTime;
import com.example.demo.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;


}
