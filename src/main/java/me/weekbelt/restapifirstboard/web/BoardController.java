package me.weekbelt.restapifirstboard.web;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.service.BoardService;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveRequestDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@RequestBody BoardSaveRequestDto boardSaveRequestDto){
        BoardSaveResponseDto boardSaveResponseDto = boardService.saveBoard(boardSaveRequestDto);
        URI createUri = linkTo(BoardController.class).slash("{id}").toUri();
        return ResponseEntity.created(createUri).body(boardSaveResponseDto);
    }
}
