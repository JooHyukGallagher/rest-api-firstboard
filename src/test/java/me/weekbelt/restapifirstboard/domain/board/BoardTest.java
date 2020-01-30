package me.weekbelt.restapifirstboard.domain.board;

import me.weekbelt.restapifirstboard.web.dto.board.RequestBoardDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BoardTest {
    @Test
    public void getBoardEntity() {
        //given
        RequestBoardDto requestBoardDto = RequestBoardDto.builder()
                .boardTitle("자유")
                .boardContent("자유 입니다.")
                .boardType(BoardType.FREE)
                .viewCount(0)
                .build();

        //when
        Board board = Board.getBoardEntity(requestBoardDto);

        //then
        assertThat(board).isNotNull();
        assertThat(board.getBoardTitle()).isEqualTo(requestBoardDto.getBoardTitle());
        assertThat(board.getBoardContent()).isEqualTo(requestBoardDto.getBoardContent());
        assertThat(board.getBoardType()).isEqualTo(requestBoardDto.getBoardType());
        assertThat(board.getViewCount()).isEqualTo(requestBoardDto.getViewCount());
    }
}