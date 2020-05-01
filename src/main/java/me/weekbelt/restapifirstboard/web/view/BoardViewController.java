package me.weekbelt.restapifirstboard.web.view;

import lombok.RequiredArgsConstructor;
import me.weekbelt.restapifirstboard.service.BoardService;
import me.weekbelt.restapifirstboard.web.dto.board.BoardReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/boards")
@Controller
public class BoardViewController {

    private final BoardService boardService;

//    @GetMapping
//    public String boardList(@PageableDefault Pageable pageable, Model model,
//                            @RequestParam(defaultValue = "ALL") String boardType) {
//        Page<BoardReadResponseDto> boardList = boardService.findBoardList(pageable, boardType);
//        model.addAttribute("boardList", boardList);
//        return "board/boardList";
//    }

    @GetMapping
    public String boardList() {
        return "board/boardList";
    }
}
