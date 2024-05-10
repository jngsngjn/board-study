package hello.boardstudy.entity.board;

import java.time.LocalDateTime;

public interface BoardList {
    Integer getBoardId();
    String getTitle();
    String getAuthor();
    LocalDateTime getCreatedDate();
    int getViewCount();
}