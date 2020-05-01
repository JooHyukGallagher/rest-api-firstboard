package me.weekbelt.restapifirstboard.web.dto.board;

import me.weekbelt.restapifirstboard.web.BoardController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class BoardReadResource extends EntityModel<BoardReadResponseDto> {
    public BoardReadResource(BoardReadResponseDto board, Link... links) {
        super(board, links);
        add(linkTo(BoardController.class).slash(board.getId()).withSelfRel());
    }
}

