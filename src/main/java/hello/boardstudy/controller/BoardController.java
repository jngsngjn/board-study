package hello.boardstudy.controller;

import hello.boardstudy.form.BoardForm;
import hello.boardstudy.entity.BoardOne;
import hello.boardstudy.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 페이지 조회
    @GetMapping
    public String boardPage(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("boardList", boardService.findBoardList(page - 1));
        return "board";
    }

    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public String boardOne(@PathVariable Long boardId, Model model) {
        BoardOne board = boardService.findBoardOne(boardId);

        if (board == null) {
            return "rediect:/board";
        }

        boardService.viewBoardOne(boardId);
        model.addAttribute("board", board);
        return "boardOne";
    }

    // 글쓰기 페이지 조회
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("writeForm", new BoardForm());
        return "writeForm";
    }

    // 글쓰기
    @PostMapping("/write")
    public String write(@ModelAttribute BoardForm boardForm) {
        boardService.writeBoard(boardForm);
        return "redirect:/board";
    }

    // 수정 페이지 조회
    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable Long boardId, Model model) {
        BoardOne board = boardService.findBoardOne(boardId);
        model.addAttribute("board", board);
        return "editForm";
    }

    // 글 수정
    @PostMapping("/{boardId}/edit")
    public String edit(@PathVariable Long boardId, @ModelAttribute BoardForm boardForm, RedirectAttributes redirectAttributes) {
        boardService.updateBoard(boardId, boardForm);

        redirectAttributes.addAttribute(boardId);
        return "redirect:/board/{boardId}";
    }

    // 글 삭제
    @PostMapping("/{boardId}/delete")
    public String deleteOne(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return "redirect:/board";
    }
}