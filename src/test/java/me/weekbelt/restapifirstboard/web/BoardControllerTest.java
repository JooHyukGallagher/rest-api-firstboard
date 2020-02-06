package me.weekbelt.restapifirstboard.web;

import me.weekbelt.restapifirstboard.BaseControllerTest;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.domain.board.BoardType;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends BaseControllerTest {
    @Autowired
    BoardController boardController;

    @Autowired
    BoardRepository boardRepository;

    @Override
    protected Object controller() {
        return boardController;
    }

    @DisplayName("게시글 생성")
    @Test
    public void createEvent() throws Exception {
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
                .andExpect(jsonPath("id").exists()).andReturn();

        //then
        Board findBoard = boardRepository.findAll().get(0);
        assertThat(findBoard.getBoardTitle()).isEqualTo(boardTitle);
        assertThat(findBoard.getBoardContent()).isEqualTo(boardContent);
        assertThat(findBoard.getAuthor()).isEqualTo(author);
        assertThat(findBoard.getBoardType()).isEqualTo(boardType);
    }
}