package me.weekbelt.restapifirstboard.web.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/boards")
@Controller
public class BoardViewController {

    @GetMapping
    public String boardList(){
        return "/board/boardList";
    }
}
