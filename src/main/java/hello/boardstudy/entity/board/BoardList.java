package hello.boardstudy.entity.board;

import java.time.LocalDateTime;

public interface BoardList {
    Integer getBoardId();
    String getTitle();
    String getLoginId();
    LocalDateTime getCreatedDate();
    int getViewCount();
}