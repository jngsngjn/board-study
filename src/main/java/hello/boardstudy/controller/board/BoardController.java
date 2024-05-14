package hello.boardstudy.controller.board;

import hello.boardstudy.entity.board.BoardOne;
import hello.boardstudy.form.WriteForm;
import hello.boardstudy.security.CustomUserDetails;
import hello.boardstudy.service.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시판 페이지 조회
    @GetMapping
    public String boardPage(@RequestParam(defaultValue = "1") int page,
                            @AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model) {

        model.addAttribute("boardList", boardService.findBoardList(page - 1));

        // 로그인한 사용자일 때만
        if (userDetails != null) {
            model.addAttribute("name", userDetails.getName());
        }
        return "board";
    }

    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public String boardOne(@PathVariable Integer boardId, Model model,
                           @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : #this")
                           CustomUserDetails userDetails) {

        BoardOne board = boardService.findBoardOne(boardId);

        if (board == null) {
            return "redirect:/boards";
        }

        // 조회수 증가
        boardService.viewBoardOne(boardId);

        model.addAttribute("board", board);
        model.addAttribute("authentication", userDetails);
        return "boardOne";
    }

    // 글쓰기 페이지 조회
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("writeForm", new WriteForm());
        return "writeForm";
    }

    // 글쓰기
    @PostMapping("/write")
    public String write(@Validated @ModelAttribute WriteForm writeForm,
                        BindingResult bindingResult,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            log.info("오류={}", bindingResult.getAllErrors());
            return "writeForm";
        }

        boardService.writeBoard(writeForm, userDetails.getUserId());
        return "redirect:/boards";
    }

    // 수정 페이지 조회
    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable Integer boardId, Model model) {
        BoardOne board = boardService.findBoardOne(boardId);

        WriteForm writeForm = new WriteForm();
        writeForm.setTitle(board.getTitle());
        writeForm.setContent(board.getContent());

        model.addAttribute("writeForm", writeForm);
        model.addAttribute("boardId", boardId);
        return "editForm";
    }

    // 글 수정
    @PutMapping("/{boardId}/edit")
    public String edit(@PathVariable Integer boardId,
                       @Validated @ModelAttribute WriteForm writeForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "editForm";
        }

        if (!boardService.updateBoard(boardId, writeForm)) {
            bindingResult.reject("noChange");
            return "editForm";
        }

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