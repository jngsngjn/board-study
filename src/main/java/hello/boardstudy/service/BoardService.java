package hello.boardstudy.service;

import hello.boardstudy.entity.Board;
import hello.boardstudy.form.BoardForm;
import hello.boardstudy.projection.BoardList;
import hello.boardstudy.projection.BoardOne;
import hello.boardstudy.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<BoardList> findBoardList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findAllBy(pageable);
    }

    public BoardOne findOneBoard(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    public void writeBoard(BoardForm boardForm) {
        Board board = new Board();
        board.setTitle(boardForm.getTitle());
        board.setAuthor(boardForm.getAuthor());
        board.setContent(boardForm.getContent());

        boardRepository.save(board);
    }

    public void viewBoardOne(Long boardId) {
        boardRepository.incrementViewCount(boardId);
    }

    public void updateBoard(Long boardId, BoardForm boardForm) {
        Optional<Board> board = boardRepository.findById(boardId);
        board.get().setTitle(boardForm.getTitle());
        board.get().setContent(boardForm.getContent());
        board.get().setAuthor(boardForm.getAuthor());
        boardRepository.save(board.get());
    }

    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}