package me.weekbelt.restapifirstboard.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardSaveRequestDto {

    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;
    @NotBlank
    private String author;
    @NotBlank
    private String boardType;

    public Board toBoardEntity(){
        return Board.builder()
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .author(this.author)
                .boardType(BoardType.valueOf(this.boardType))
                .viewCount(0)
                .build();
    }
}
