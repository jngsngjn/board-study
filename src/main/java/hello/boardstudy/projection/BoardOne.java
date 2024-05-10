package hello.boardstudy.projection;

import java.time.LocalDateTime;

public interface BoardOne {
    Long getBoardId();
    String getTitle();
    String getAuthor();
    String getContent();
    LocalDateTime getCreatedDate();
    LocalDateTime getModifiedDate();
    int getViewCount();
}