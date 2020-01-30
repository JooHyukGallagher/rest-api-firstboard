package me.weekbelt.restapifirstboard.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.config.auth.SessionUser;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.domain.user.Role;
import me.weekbelt.restapifirstboard.domain.user.User;
import me.weekbelt.restapifirstboard.domain.user.UserRepository;
import me.weekbelt.restapifirstboard.web.dto.board.RequestBoardDto;
import me.weekbelt.restapifirstboard.web.dto.board.ResponseBoardDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public ResponseBoardDto save(RequestBoardDto requestBoardDto) {
        Board board = Board.getBoardEntity(requestBoardDto);
        Board savedBoard = boardRepository.save(board);

        return ResponseBoardDto.getResponseBoardDto(savedBoard);
    }
}
