package com.example.demo.domain.domain.board.domain;

import com.example.demo.enums.board.BoardStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    public Board(String name, BoardStatus boardStatus) {
        this.name = name;
        this.boardStatus = boardStatus;
    }

    public void update(String name) {
        this.name = name;
    }

    public void delete(String password) {
        this.boardStatus = BoardStatus.NOT_OPERATING;
    }
}
