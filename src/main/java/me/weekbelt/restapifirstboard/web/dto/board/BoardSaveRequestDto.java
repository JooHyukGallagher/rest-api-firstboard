package me.weekbelt.restapifirstboard.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardType;
import me.weekbelt.restapifirstboard.domain.user.User;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardSaveRequestDto {

    @NotBlank(message = "제목을 입하세요.")
    private String boardTitle;
    @NotBlank(message = "내용을 입력하세요.")
    private String boardContent;
    @NotBlank
    private String boardType;

    public Board toBoardEntity(User user){
        return Board.builder()
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .boardType(BoardType.valueOf(this.boardType))
                .user(user)
                .viewCount(0)
                .build();
    }
}
