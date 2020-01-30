package me.weekbelt.restapifirstboard.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.weekbelt.restapifirstboard.domain.board.BoardType;
import me.weekbelt.restapifirstboard.web.dto.board.RequestBoardDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("정상적으로 게시글을 생성하는 테스트")
    @Test
    public void createBoard() throws Exception {
        //given
        RequestBoardDto requestBoardDto = RequestBoardDto.builder()
                .boardTitle("자유")
                .boardContent("자유 입니다.")
                .boardType(BoardType.FREE)
                .viewCount(0)
                .build();

        //when
        mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestBoardDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        ;
    }

    @DisplayName("입력값이 비어있는 경우에 에러가 발생하는 테스트")
    @Test
    public void createBoardBadRequestEmptyInput() throws Exception {
        //given
        RequestBoardDto requestBoardDto = RequestBoardDto.builder()
                .build();

        //when
        mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(requestBoardDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
}