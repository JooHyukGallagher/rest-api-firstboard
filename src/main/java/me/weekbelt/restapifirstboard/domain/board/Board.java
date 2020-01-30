package me.weekbelt.restapifirstboard.domain.board;

import lombok.*;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;
import me.weekbelt.restapifirstboard.domain.reply.Reply;
import me.weekbelt.restapifirstboard.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
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
    private Integer viewCount = 0;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardType boardType = BoardType.FREE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();
}
