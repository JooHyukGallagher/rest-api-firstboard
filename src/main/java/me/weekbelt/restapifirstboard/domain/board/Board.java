package me.weekbelt.restapifirstboard.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveRequestDto;
import me.weekbelt.restapifirstboard.web.dto.board.BoardSaveResponseDto;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 200, nullable = false)
    private String boardTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String boardContent;

    private String author;

    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Builder
    public Board(String boardTitle, String boardContent, String author,
                 Integer viewCount, BoardType boardType){
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.author = author;
        this.viewCount = viewCount;
        this.boardType = boardType;
    }

    // == 비즈니스 로직 == //
    public BoardSaveResponseDto toBoardSaveResponseDto(){
        return BoardSaveResponseDto.builder()
                .id(this.id)
                .boardTitle(this.boardTitle)
                .boardContent(this.boardContent)
                .author(this.author)
                .boardType(this.boardType)
                .build();
    }

    public void plusViewCount(){
        this.viewCount++;
    }
}
