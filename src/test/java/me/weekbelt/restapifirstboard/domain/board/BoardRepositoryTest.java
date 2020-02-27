package me.weekbelt.restapifirstboard.domain.board;

import me.weekbelt.restapifirstboard.domain.user.User;
import me.weekbelt.restapifirstboard.domain.user.UserRepository;
import me.weekbelt.restapifirstboard.domain.user.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void cleanBefore() {
        userRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @AfterEach
    public void cleanAfter() {
        userRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @DisplayName("게시글저장 및 불러오기")
    @Test
    public void create_read() throws Exception {
        //given
        User user = User.builder()
                .name("김주혁")
                .email("vfrvfr4207@gmail.com")
                .userType(UserType.USER)
                .build();

        User savedUser = userRepository.save(user);

        String boardTitle = "자유";
        String boardContent = "자유 입니다.";
        BoardType boardType = BoardType.FREE;

        Board board = Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType)
                .user(savedUser)
                .build();
        boardRepository.save(board);

        //when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board findBoard = boardList.get(0);
        assertThat(findBoard.getBoardTitle()).isEqualTo(boardTitle);
        assertThat(findBoard.getBoardContent()).isEqualTo(boardContent);
        assertThat(findBoard.getUser()).isEqualTo(user);
        assertThat(findBoard.getBoardType()).isEqualTo(boardType);
    }
}