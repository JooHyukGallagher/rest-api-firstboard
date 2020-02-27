package me.weekbelt.restapifirstboard.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.restapifirstboard.domain.BaseTimeEntity;
import me.weekbelt.restapifirstboard.domain.board.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();

    @Builder
    public User(String name, String password, String email, String picture, UserType userType) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.userType = userType;
    }

}
