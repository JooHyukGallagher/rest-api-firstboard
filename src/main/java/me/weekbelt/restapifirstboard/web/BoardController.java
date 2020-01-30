package me.weekbelt.restapifirstboard.web;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.config.auth.SessionUser;
import me.weekbelt.restapifirstboard.service.BoardService;
import me.weekbelt.restapifirstboard.web.dto.board.RequestBoardDto;
import me.weekbelt.restapifirstboard.web.dto.board.ResponseBoardDto;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/board", produces = MediaTypes.HAL_JSON_VALUE)
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ResponseBoardDto> createBoard(@RequestBody @Valid RequestBoardDto requestBoardDto,
                                         Errors errors) {

        ResponseBoardDto savedBoard = boardService.save(requestBoardDto);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(BoardController.class).slash(savedBoard.getId());
        URI createUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createUri).body(savedBoard);
    }


}
