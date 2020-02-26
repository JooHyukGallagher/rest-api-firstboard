package me.weekbelt.restapifirstboard.domain.board;

public enum BoardType {
    NOTICE("공지"),
    FREE("자유"),
    QUESTION("질문"),
    PROMOTION("홍보");

    private String value;

    BoardType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
