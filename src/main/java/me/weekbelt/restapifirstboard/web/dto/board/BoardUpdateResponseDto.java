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
public class BoardUpdateResponseDto {
    private Long id;
    private String boardTitle;
    private String boardContent;
    private BoardType boardType;
}
