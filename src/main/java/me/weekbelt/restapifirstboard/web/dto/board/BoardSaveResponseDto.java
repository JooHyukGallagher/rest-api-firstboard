package me.weekbelt.restapifirstboard.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.BoardType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardSaveResponseDto {
    private Long id;
    private String boardTitle;
    private String boardContent;
    private String author;
    private BoardType boardType;
}
