package me.weekbelt.restapifirstboard.domain.reply;

import lombok.*;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;
import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.user.User;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor
@Getter
@Entity
public class Reply extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Lob
    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
