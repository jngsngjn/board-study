package hello.boardstudy.repository.board;

import hello.boardstudy.entity.board.Board;
import hello.boardstudy.entity.board.BoardList;
import hello.boardstudy.entity.board.BoardOne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT b.boardId AS boardId, b.title AS title, u.loginId AS loginId, b.createdDate AS createdDate, b.viewCount AS viewCount " +
            "FROM Board b " +
            "JOIN User u ON b.authorId = u.userId")
    Page<BoardList> findBoardListWithAuthorLoginId(Pageable pageable);

    BoardOne findByBoardId(Integer boardId);

    @Modifying
    @Query("UPDATE Board b SET b.viewCount = b.viewCount + 1 WHERE b.boardId = :boardId")
    @Transactional
    void incrementViewCount(@Param("boardId") Integer boardId);

}