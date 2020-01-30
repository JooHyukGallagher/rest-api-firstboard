package me.weekbelt.restapifirstboard.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.BoardType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter
public class RequestBoardDto {
    @NotEmpty
    private String boardTitle;
    @NotEmpty
    private String boardContent;
    @NotNull
    private Integer viewCount;
    @NotNull
    private BoardType boardType;
}
