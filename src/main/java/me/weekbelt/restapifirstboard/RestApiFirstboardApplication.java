package me.weekbelt.restapifirstboard;

import me.weekbelt.restapifirstboard.domain.board.Board;
import me.weekbelt.restapifirstboard.domain.board.BoardRepository;
import me.weekbelt.restapifirstboard.domain.board.BoardType;
import me.weekbelt.restapifirstboard.domain.user.User;
import me.weekbelt.restapifirstboard.domain.user.UserRepository;
import me.weekbelt.restapifirstboard.domain.user.UserType;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.IntStream;


@EnableJpaAuditing
@SpringBootApplication
public class RestApiFirstboardApplication {


    public static void main(String[] args) {
        SpringApplication.run(RestApiFirstboardApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository,
                                    BoardRepository boardRepository) throws Exception {
        return (args) -> {
            User user = userRepository.save(User.builder()
                    .name("김주혁")
                    .email("vfrvfr4207@gmail.com")
                    .userType(UserType.USER)
                    .build());

            IntStream.rangeClosed(1, 50).forEach(index -> {
                boardRepository.save(Board.builder()
                        .boardTitle("공지 " + index)
                        .boardContent("공지 입니다.")
                        .viewCount(0)
                        .user(user)
                        .boardType(BoardType.NOTICE)
                        .build());
            });
            IntStream.rangeClosed(1, 50).forEach(index -> {
                boardRepository.save(Board.builder()
                        .boardTitle("자유 " + index)
                        .boardContent("자유입니다.")
                        .viewCount(0)
                        .user(user)
                        .boardType(BoardType.FREE)
                        .build());
            });
            IntStream.rangeClosed(1, 50).forEach(index -> {
                boardRepository.save(Board.builder()
                        .boardTitle("질문 " + index)
                        .boardContent("질문 입니다.")
                        .viewCount(0)
                        .user(user)
                        .boardType(BoardType.QUESTION)
                        .build());
            });
            IntStream.rangeClosed(1, 50).forEach(index -> {
                boardRepository.save(Board.builder()
                        .boardTitle("홍보 " + index)
                        .boardContent("홍보 입니다.")
                        .viewCount(0)
                        .user(user)
                        .boardType(BoardType.PROMOTION)
                        .build());
            });
        };
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
