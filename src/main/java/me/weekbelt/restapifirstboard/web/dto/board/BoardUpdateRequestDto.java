package me.weekbelt.restapifirstboard.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardUpdateRequestDto {
    @NotBlank
    private String boardTitle;
    @NotBlank
    private String boardContent;
    @NotBlank
    private String boardType;
}
