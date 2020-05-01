package me.weekbelt.restapifirstboard.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.config.auth.dto.SessionUser;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardFactoryObject;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.domain.board.BoardType;
import me.weekbelt.restapifirstboard.domain.user.User;
import me.weekbelt.restapifirstboard.domain.user.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public BoardSaveResponseDto saveBoard(SessionUser sessionUser,
                                          BoardSaveRequestDto boardSaveRequestDto) {
        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 회원정보가 없습니다. email=" + sessionUser.getEmail()));

        Board board = boardSaveRequestDto.toBoardEntity(user);
        Board savedBoard = boardRepository.save(board);
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

    public Page<BoardReadResponseDto> findBoardList(Pageable pageable, String boardType) {
//        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.
//                getPageNumber() - 1, pageable.getPageSize());

        Page<Board> boardList = getBoardList(pageable, boardType);

        return boardList.map(BoardFactoryObject::toBoardReadResponseDto);
    }

    private Page<Board> getBoardList(Pageable pageable, String boardType) {
        Page<Board> boardList;
        if (boardType.equals("ALL")) {
            boardList = boardRepository.findAll(pageable);
        } else {
            boardList = boardRepository.findAllByBoardType(pageable, BoardType.valueOf(boardType));
        }
        return boardList;
    }
}
