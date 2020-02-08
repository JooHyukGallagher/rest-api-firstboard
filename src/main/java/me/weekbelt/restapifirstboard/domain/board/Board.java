package me.weekbelt.restapifirstboard.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;
import me.weekbelt.restapifirstboard.web.dto.board.*;

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
    public void plusViewCount(){
        this.viewCount++;
    }

    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        this.boardTitle = boardUpdateRequestDto.getBoardTitle();
        this.boardContent = boardUpdateRequestDto.getBoardContent();
        this.boardType = BoardType.valueOf(boardUpdateRequestDto.getBoardType());
    }
}
