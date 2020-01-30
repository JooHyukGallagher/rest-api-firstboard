package me.weekbelt.restapifirstboard.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardType;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseBoardDto {

    private Long id;
    private String boardTitle;
    private String boardContent;
    private Integer viewCount;
    private BoardType boardType;

    // == 비즈니스 로직 ==//
    public static ResponseBoardDto getResponseBoardDto(Board savedBoard) {
        return ResponseBoardDto.builder()
                .id(savedBoard.getId())
                .boardTitle(savedBoard.getBoardTitle())
                .boardContent(savedBoard.getBoardContent())
                .viewCount(savedBoard.getViewCount())
                .boardType(savedBoard.getBoardType())
                .build();
    }
}
