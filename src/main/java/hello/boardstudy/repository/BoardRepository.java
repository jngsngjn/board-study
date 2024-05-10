package hello.boardstudy.repository;

import hello.boardstudy.entity.Board;
import hello.boardstudy.projection.BoardList;
import hello.boardstudy.projection.BoardOne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<BoardList> findByDeletedFalse(Pageable pageable);

    BoardOne findByDeletedFalseAndBoardId(Long boardId);
    Board findByBoardIdAndDeletedFalse(Long boardId);

    @Modifying
    @Query("UPDATE Board b SET b.viewCount = b.viewCount + 1 WHERE b.boardId = :boardId")
    @Transactional
    void incrementViewCount(@Param("boardId") Long boardId);


}