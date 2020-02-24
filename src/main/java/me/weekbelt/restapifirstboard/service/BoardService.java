package me.weekbelt.restapifirstboard.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardFactoryObject;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.web.dto.board.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponseDto saveBoard(BoardSaveRequestDto boardSaveRequestDto) {
        Board savedBoard = boardRepository.save(boardSaveRequestDto.toBoardEntity());

        return BoardFactoryObject.toBoardSaveResponseDto(savedBoard);
    }

    @Transactional
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board findBord = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다. id=" + boardId));

        findBord.update(boardUpdateRequestDto);

        Board updatedBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다. id=" + boardId));

        return BoardFactoryObject.toBoardUpdateResponseDto(updatedBoard);
    }

    public BoardReadResponseDto readBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다. id=" + boardId));

        findBoard.plusViewCount();

        return BoardFactoryObject.toBoardReadResponseDto(findBoard);
    }

//    public Page<BoardReadResponseDto> findBoardList(Pageable pageable) {
//        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.
//                getPageNumber() - 1, pageable.getPageSize());
//
//        Page<Board> boardPage = boardRepository.findAll(pageable);
//
//    }
}
