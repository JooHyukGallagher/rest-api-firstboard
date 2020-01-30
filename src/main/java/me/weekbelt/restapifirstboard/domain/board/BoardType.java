package me.weekbelt.restapifirstboard.domain.board;

import lombok.Getter;

@Getter
public enum BoardType {
    ANNOUNCE("공지"),
    FREE("자유"),
    QUESTION("질문"),
    PROMOTION("홍보");

    private String boardType;

    BoardType(String boardType) {
        this.boardType = boardType;
    }
}
