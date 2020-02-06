package me.weekbelt.restapifirstboard.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;

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

    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Builder
    public Board(String boardTitle, String boardContent, Integer viewCount, BoardType boardType){
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.viewCount = viewCount;
        this.boardType = boardType;
    }

    public void plusViewCount(){
        this.viewCount++;
    }
}
