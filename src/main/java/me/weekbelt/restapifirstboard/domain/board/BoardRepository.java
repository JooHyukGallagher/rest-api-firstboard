package me.weekbelt.restapifirstboard.domain.board;

import me.weekbelt.restapifirstboard.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
