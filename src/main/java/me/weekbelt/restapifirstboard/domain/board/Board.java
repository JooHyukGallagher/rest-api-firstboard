package me.weekbelt.restapifirstboard.domain.board;

import lombok.*;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;
import me.weekbelt.restapifirstboard.domain.reply.Reply;
import me.weekbelt.restapifirstboard.domain.user.User;
import me.weekbelt.restapifirstboard.web.dto.board.RequestBoardDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String boardTitle;

    @Column(nullable = false)
    @Lob
    private String boardContent;

    @Column(nullable = false)
    private Integer viewCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Board(String boardTitle, String boardContent, Integer viewCount, BoardType boardType) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.viewCount = viewCount;
        this.boardType = boardType;
    }

    // 연관 관계
    public void setUser(User user) {
        this.user = user;
        user.getBoards().add(this);
    }

    // == 비즈니스 로직 == //
    public static Board getBoardEntity(RequestBoardDto requestBoardDto) {
        return Board.builder()
                .boardTitle(requestBoardDto.getBoardTitle())
                .boardContent(requestBoardDto.getBoardContent())
                .boardType(requestBoardDto.getBoardType())
                .viewCount(requestBoardDto.getViewCount())
                .build();
    }

    public void update(String boardTitle, String boardContent, BoardType boardType) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardType = boardType;
    }

    public void plusViewCount(){
        this.viewCount++;
    }
}
