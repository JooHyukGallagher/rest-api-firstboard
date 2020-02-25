package me.weekbelt.restapifirstboard.web;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.common.ErrorResource;
import me.weekbelt.restapifirstboard.service.BoardService;
import me.weekbelt.restapifirstboard.web.dto.board.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@Controller
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> saveBoard(@RequestBody @Valid BoardSaveRequestDto boardSaveRequestDto,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        BoardSaveResponseDto boardSaveResponseDto = boardService.saveBoard(boardSaveRequestDto);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(BoardController.class).slash(boardSaveResponseDto.getId());
        URI createUri = selfLinkBuilder.toUri();

        BoardSaveResource boardSaveResource = new BoardSaveResource(boardSaveResponseDto);
        return ResponseEntity.created(createUri).body(boardSaveResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable(name = "id") Long boardId,
                                         @RequestBody @Valid BoardUpdateRequestDto boardUpdateRequestDto,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return badRequest(bindingResult);
        }

        BoardUpdateResponseDto boardUpdateResponseDto = boardService.updateBoard(boardId, boardUpdateRequestDto);
        BoardUpdateResource boardUpdateResource = new BoardUpdateResource(boardUpdateResponseDto);

        return ResponseEntity.ok(boardUpdateResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readBoard(@PathVariable(name = "id") Long boardId) {
        BoardReadResponseDto boardReadResponseDto = boardService.readBoard(boardId);
        BoardReadResource boardReadResource = new BoardReadResource(boardReadResponseDto);

        return ResponseEntity.ok(boardReadResource);
    }

    @GetMapping
    public ResponseEntity<?> getBoards(@PageableDefault Pageable pageable,
                                       PagedResourcesAssembler<BoardReadResponseDto> assembler) {
        Page<BoardReadResponseDto> page = boardService.findBoardList(pageable);
        PagedModel<BoardReadResource> boardReadResources = assembler.toModel(page, b -> new BoardReadResource(b));
        return ResponseEntity.ok(boardReadResources);
    }

    private ResponseEntity<?> badRequest(BindingResult bindingResult) {
        return ResponseEntity.badRequest().body(new ErrorResource(bindingResult));
    }
}
