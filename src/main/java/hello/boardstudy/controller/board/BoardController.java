package hello.boardstudy.controller.board;

import hello.boardstudy.form.BoardForm;
import hello.boardstudy.entity.board.BoardOne;
import hello.boardstudy.security.CustomUserDetails;
import hello.boardstudy.service.board.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/boards")
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
    public String boardOne(@PathVariable Integer boardId, Model model) {
        BoardOne board = boardService.findBoardOne(boardId);

        if (board == null) {
            return "redirect:/boards";
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
    public String write(@ModelAttribute BoardForm boardForm, @AuthenticationPrincipal CustomUserDetails userDetails) {
        boardForm.setAuthorId(userDetails.getUserId());
        boardService.writeBoard(boardForm);
        return "redirect:/boards";
    }

    // 수정 페이지 조회
    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable Integer boardId, Model model) {
        BoardOne board = boardService.findBoardOne(boardId);
        model.addAttribute("board", board);
        return "editForm";
    }

    // 글 수정
    @PutMapping("/{boardId}/edit")
    public String edit(@PathVariable Integer boardId, @ModelAttribute BoardForm boardForm, RedirectAttributes redirectAttributes) {
        boardService.updateBoard(boardId, boardForm);

        redirectAttributes.addAttribute(boardId);
        return "redirect:/boards/{boardId}";
    }

    // 글 삭제
    @DeleteMapping("/{boardId}/delete")
    public String deleteOne(@PathVariable Integer boardId) {
        boardService.delete(boardId);
        return "redirect:/boards";
    }
}