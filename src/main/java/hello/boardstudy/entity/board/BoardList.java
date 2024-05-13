package hello.boardstudy.entity.board;

import java.time.LocalDate;

public interface BoardList {
    Integer getBoardId();
    String getTitle();
    String getLoginId();

    LocalDate getCreatedDate();

    int getViewCount();
}