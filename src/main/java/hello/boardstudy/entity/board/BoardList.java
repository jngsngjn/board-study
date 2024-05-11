package hello.boardstudy.entity.board;

import java.time.LocalDateTime;

public interface BoardList {
    Integer getBoardId();
    String getTitle();
    Integer getAuthorId();
    LocalDateTime getCreatedDate();
    int getViewCount();
}