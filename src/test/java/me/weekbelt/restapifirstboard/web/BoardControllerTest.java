package me.weekbelt.restapifirstboard.web;

import me.weekbelt.restapifirstboard.BaseControllerTest;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.domain.board.BoardType;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveRequestDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BoardControllerTest extends BaseControllerTest {
    @Autowired
    BoardController boardController;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    EntityManager entityManager;

    @Override
    protected Object controller() {
        return boardController;
    }

    @BeforeEach
    public void initBoard() {
        boardRepository.deleteAll();
    }

    @DisplayName("게시글 생성")
    @Test
    public void createBoard() throws Exception {
        //given
        String boardTitle = "자유";
        String boardContent = "자유입니다.";
        BoardType boardType = BoardType.FREE;
        String author = "김주혁";

        BoardSaveRequestDto boardSaveRequestDto = BoardSaveRequestDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType.name())
                .author(author)
                .build();

        //when
        mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardSaveRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
        ;

        //then
        Board findBoard = boardRepository.findAll().get(0);
        assertThat(findBoard.getBoardTitle()).isEqualTo(boardTitle);
        assertThat(findBoard.getBoardContent()).isEqualTo(boardContent);
        assertThat(findBoard.getAuthor()).isEqualTo(author);
        assertThat(findBoard.getBoardType()).isEqualTo(boardType);
    }

    @DisplayName("게시글 생성시 잘못된 값을 입력했을때 에러 발생")
    @ParameterizedTest(name = "{index} {displayName}")
    @CsvSource({
            ",,,,", " , , , ,"
    })
    public void createBoard_Bad_Request_Empty_Wrong(String boardTitle,
                                                    String boardContent,
                                                    String author,
                                                    String boardType) throws Exception {
        //given
        BoardSaveRequestDto boardSaveRequestDto = BoardSaveRequestDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .author(author)
                .boardType(boardType)
                .build();

        //when
        mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardSaveRequestDto)))
                .andExpect(status().isBadRequest())
        ;
    }

    @DisplayName("게시글 수정")
    @Test
    public void modifyBoard() throws Exception {
        //given
        Board board = generateBoard();

        String boardTitle = "공지";
        String boardContent = "공지 입니다.";
        BoardType boardType = BoardType.NOTICE;

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType.name())
                .build();

        //when
        mockMvc.perform(put("/api/board/" + board.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andExpect(status().isOk());

        //then
        Board findBoard = boardRepository.findAll().get(0);
        assertThat(findBoard.getBoardTitle()).isEqualTo(boardTitle);
        assertThat(findBoard.getBoardContent()).isEqualTo(boardContent);
        assertThat(findBoard.getBoardType()).isEqualTo(boardType);
    }

    @DisplayName("잘못된 값으로 게시글 수정")
    @ParameterizedTest(name = "{index} {displayName}")
    @CsvSource({
            " , , ,", ",,,"
    })
    public void modifyBoard_Bad_Request_Wrong(String boardTitle,
                                              String boardContent,
                                              String boardType) throws Exception {
        //given
        Board board = generateBoard();

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType)
                .build();

        //when
        mockMvc.perform(put("/api/board/" + board.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("boardId에 따른 게시글 조회")
    @Test
    public void readBoard() throws Exception {
        //given
        Board board = generateBoard();

        //when
        mockMvc.perform(get("/api/board/" + board.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(board.getId()))
                .andExpect(jsonPath("boardTitle").value(board.getBoardTitle()))
                .andExpect(jsonPath("boardContent").value(board.getBoardContent()))
                .andExpect(jsonPath("viewCount").value(board.getViewCount()+1))
                .andExpect(jsonPath("author").value(board.getAuthor()))
                .andExpect(jsonPath("boardType").value(board.getBoardType().name()))
        ;
    }


    public Board generateBoard() {
        String boardTitle = "자유";
        String boardContent = "자유입니다.";
        BoardType boardType = BoardType.FREE;
        String author = "김주혁";

        Board board = Board.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .author(author)
                .boardType(boardType)
                .viewCount(0)
                .build();

        return this.boardRepository.save(board);
    }
}