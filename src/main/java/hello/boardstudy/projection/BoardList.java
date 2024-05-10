package hello.boardstudy.projection;

import java.time.LocalDateTime;

public interface BoardList {
    Long getBoardId();
    String getTitle();
    String getAuthor();
    LocalDateTime getCreatedDate();
    int getViewCount();
}