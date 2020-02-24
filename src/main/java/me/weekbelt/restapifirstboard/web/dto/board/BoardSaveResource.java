package me.weekbelt.restapifirstboard.web.dto.board;

import me.weekbelt.restapifirstboard.web.BoardController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class BoardSaveResource extends EntityModel<BoardSaveResponseDto> {

    public BoardSaveResource(BoardSaveResponseDto board, Link... links) {
        super(board, links);
        add(linkTo(BoardController.class).slash(board.getId()).withSelfRel());
        add(linkTo(BoardController.class).withRel("query-boards"));
        add(linkTo(BoardController.class).slash(board.getId()).withRel("update-board"));
    }
}
