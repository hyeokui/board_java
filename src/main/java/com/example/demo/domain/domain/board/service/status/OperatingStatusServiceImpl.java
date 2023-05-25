package com.example.demo.domain.domain.board.service.status;

import com.example.demo.domain.domain.board.domain.BoardRepository;
import com.example.demo.enums.board.BoardStatus;
import com.example.demo.exception.board.BoardNotFoundException;
import com.example.demo.exception.board.BoardNotOperatingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OperatingStatusServiceImpl implements OperatingStatusService {

    private final BoardRepository boardRepository;

    @Override
    public void isBoardOperating(String boardName) {
        boardRepository.findByName(boardName).ifPresentOrElse(board -> {
                    if (board.getBoardStatus() != BoardStatus.OPERATING) {
                        throw new BoardNotOperatingException();
                    }
                },
                () -> {
                    throw new BoardNotFoundException();
                }
        );
    }
}
