package me.weekbelt.restapifirstboard.domain.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    public void cleanup() {
        boardRepository.deleteAll();
    }

    @DisplayName("게시글저장 및 불러오기")
    @Test
    public void create_read() throws Exception {
        //given
        String boardTitle = "자유";
        String boardContent = "자유 입니다.";
        BoardType boardType = BoardType.FREE;

        Board board = Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType)
                .build();
        boardRepository.save(board);

        //when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board findBoard = boardList.get(0);
        assertThat(findBoard.getBoardTitle()).isEqualTo(boardTitle);
        assertThat(findBoard.getBoardContent()).isEqualTo(boardContent);
        assertThat(findBoard.getBoardType()).isEqualTo(boardType);
    }
}