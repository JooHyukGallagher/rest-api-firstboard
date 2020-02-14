package me.weekbelt.restapifirstboard.web.dto.board;

import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.web.BoardController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class BoardResource extends EntityModel<BoardSaveResponseDto> {
    public BoardResource(BoardSaveResponseDto board, Link... links) {
        super(board, links);
        add(linkTo(BoardController.class).slash(board.getId()).withSelfRel());
    }
}
