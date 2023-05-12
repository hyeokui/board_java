package com.example.demo.domain.domain.board.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
