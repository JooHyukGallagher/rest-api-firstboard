package me.weekbelt.restapifirstboard.web;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.service.BoardService;
import me.weekbelt.restapifirstboard.web.dto.board.*;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping("/api/board")
@Controller
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@RequestBody @Valid BoardSaveRequestDto boardSaveRequestDto,
                                       BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        BoardSaveResponseDto boardSaveResponseDto = boardService.saveBoard(boardSaveRequestDto);
        URI createUri = linkTo(BoardController.class).slash(boardSaveResponseDto.getId()).toUri();
        return ResponseEntity.created(createUri).body(boardSaveResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable(name = "id") Long boardId,
                                         @RequestBody @Valid BoardUpdateRequestDto boardUpdateRequestDto,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        BoardUpdateResponseDto boardUpdateResponseDto = boardService.updateBoard(boardId, boardUpdateRequestDto);
        return ResponseEntity.ok(boardUpdateResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readBoard(@PathVariable(name = "id") Long boardId){
        BoardReadResponseDto boardReadResponseDto = boardService.readBoard(boardId);
        return ResponseEntity.ok(boardReadResponseDto);
    }
}
