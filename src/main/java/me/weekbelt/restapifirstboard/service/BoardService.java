package me.weekbelt.restapifirstboard.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveRequestDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveResponseDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardUpdateRequestDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardUpdateResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto){
        Board savedBoard = boardRepository.save(boardSaveRequestDto.toBoardEntity());
        return savedBoard.toBoardSaveResponseDto();
    }

    @Transactional
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto){
        Board findBord = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다. id=" + boardId));

        findBord.update(boardUpdateRequestDto);

        Board updatedBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다. id=" + boardId));

        return updatedBoard.toBoardUpdateResponseDto();
    }

//    public BoardReadResonseDto readBoard(Long boardId){
//        Board findBoard = boardRepository.findById(boardId)
//                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다. id=" + boardId));
//
//        findBoard.plusViewCount();
//
//        return findBoard.toBoardReadResponseDto();
//    }
}
