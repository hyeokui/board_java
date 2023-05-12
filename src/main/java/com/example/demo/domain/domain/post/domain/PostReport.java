package com.example.demo.domain.domain.post.domain;

import com.example.demo.domain.domain.time.domain.BaseTime;
import com.example.demo.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostReport extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

}
