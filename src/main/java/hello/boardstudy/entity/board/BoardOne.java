package hello.boardstudy.entity.board;

import java.time.LocalDateTime;

public interface BoardOne {
    Integer getBoardId();
    String getTitle();
    String getContent();
    LocalDateTime getCreatedDate();
    LocalDateTime getModifiedDate();
    int getViewCount();

    Integer getAuthorId();
}