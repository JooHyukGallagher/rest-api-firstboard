package me.weekbelt.restapifirstboard.domain.board;

import me.weekbelt.restapifirstboard.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByUser(User user);

    Page<Board> findAllByBoardType(Pageable pageable, BoardType boardType);
}