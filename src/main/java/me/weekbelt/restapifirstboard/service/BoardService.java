package me.weekbelt.restapifirstboard.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveRequestDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveResponseDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto){
        Board savedBoard = boardRepository.save(boardSaveRequestDto.toBoardEntity());
        return savedBoard.toBoardSaveResponseDto();
    }
}
