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

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BoardControllerTest extends BaseControllerTest {
    @Autowired
    BoardController boardController;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    EntityManager entityManager;

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
        mockMvc.perform(post("/api/boards")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardSaveRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-boards").exists())
                .andExpect(jsonPath("_links.update-board").exists())
                .andDo(document("create-board",
                        links(
                                linkWithRel("self").description("현재 링크 표시"),
                                linkWithRel("query-boards").description(""),
                                linkWithRel("update-board").description("현재 게시글을 수정하는 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더"),
                                headerWithName(HttpHeaders.ACCEPT).description("Accept 헤더"),
                                headerWithName(HttpHeaders.CONTENT_LENGTH).description("Content-Length 헤더")
                        ),
                        requestFields(
                                fieldWithPath("boardTitle").description("게시글 제목"),
                                fieldWithPath("boardContent").description("게시글 내용"),
                                fieldWithPath("author").description("게시글 작성자 이름"),
                                fieldWithPath("boardType").description("게시글의 카테고리 분류")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location 헤더"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type 헤더")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시글의 식별자"),
                                fieldWithPath("boardTitle").description("게시글 제목"),
                                fieldWithPath("boardContent").description("게시글 내용"),
                                fieldWithPath("author").description("게시글 작성자 이름"),
                                fieldWithPath("boardType").description("게시글의 카테고리 분류"),
                                fieldWithPath("_links.self.href").description("현재 리소스 링크"),
                                fieldWithPath("_links.query-boards.href").description(""),
                                fieldWithPath("_links.update-board.href").description("현재 게시글의 수정 링크")
                        )
                ))
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
        mockMvc.perform(post("/api/boards")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardSaveRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("content[0].objectName").exists())
                .andExpect(jsonPath("content[0].defaultMessage").exists())
                .andExpect(jsonPath("content[0].code").exists())
        ;
    }

    @DisplayName("게시글 수정")
    @Test
    public void modifyBoard() throws Exception {
        //given
        Board board = generateBoard("자유", "자유입니다.",
                BoardType.FREE, "김주혁");

        String boardTitle = "공지";
        String boardContent = "공지 입니다.";
        BoardType boardType = BoardType.NOTICE;

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType.name())
                .build();

        //when
        mockMvc.perform(put("/api/boards/" + board.getId())
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
        Board board = generateBoard("자유", "자유입니다.",
                BoardType.FREE, "김주혁");

        BoardUpdateRequestDto boardUpdateRequestDto = BoardUpdateRequestDto.builder()
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardType(boardType)
                .build();

        //when
        mockMvc.perform(put("/api/boards/" + board.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsString(boardUpdateRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("게시글 리스트 조회")
    @Test
    public void queryBoards() throws Exception {
        //given
        IntStream.range(0, 10).forEach(number -> {
            generateBoard("자유 " + number, "자유 입니다.",
                    BoardType.FREE, "김주혁");
        });
        IntStream.range(0, 10).forEach(number -> {
            generateBoard("공지 " + number, "공지 입니다.",
                    BoardType.NOTICE, "김주혁");
        });
        IntStream.range(0, 10).forEach(number -> {
            generateBoard("홍보 " + number, "홍보 입니다.",
                    BoardType.PROMOTION, "김주혁");
        });

        //when
        this.mockMvc.perform(get("/api/boards")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "id,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.boardReadResponseDtoList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
        ;
        //then
    }

    @DisplayName("boardId에 따른 게시글 조회")
    @Test
    public void readBoard() throws Exception {
        //given
        Board board = generateBoard("자유", "자유입니다.",
                BoardType.FREE, "김주혁");

        //when
        mockMvc.perform(get("/api/boards/" + board.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(board.getId()))
                .andExpect(jsonPath("boardTitle").value(board.getBoardTitle()))
                .andExpect(jsonPath("boardContent").value(board.getBoardContent()))
                .andExpect(jsonPath("viewCount").value(board.getViewCount() + 1))
                .andExpect(jsonPath("author").value(board.getAuthor()))
                .andExpect(jsonPath("boardType").value(board.getBoardType().name()))
        ;
    }

    private Board generateBoard(String boardTitle, String boardContent,
                                BoardType boardType, String author) {
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