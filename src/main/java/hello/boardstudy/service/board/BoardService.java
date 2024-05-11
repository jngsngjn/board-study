package hello.boardstudy.service.board;

import hello.boardstudy.entity.board.Board;
import hello.boardstudy.form.BoardForm;
import hello.boardstudy.entity.board.BoardList;
import hello.boardstudy.entity.board.BoardOne;
import hello.boardstudy.repository.board.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 모든 게시글 조회
    public Page<BoardList> findBoardList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return boardRepository.findBoardListWithAuthorLoginId(pageable);
    }

    // 특정 게시글 조회
    public BoardOne findBoardOne(Integer boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    // 조회수 업데이트
    public void viewBoardOne(Integer boardId) {
        boardRepository.incrementViewCount(boardId);
    }

    // 글 쓰기
    public void writeBoard(BoardForm boardForm) {
        Board board = new Board();
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setAuthorId(boardForm.getAuthorId());

        boardRepository.save(board);
    }

    // 글 수정
    public void updateBoard(Integer boardId, BoardForm boardForm) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());

        boardRepository.save(board);
    }

    // 글 삭제
    public void delete(Integer boardId) {
        boardRepository.deleteById(boardId);
    }
}