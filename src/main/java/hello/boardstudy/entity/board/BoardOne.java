package hello.boardstudy.entity.board;

import java.time.LocalDate;

public interface BoardOne {
    Integer getBoardId();
    String getTitle();
    String getContent();
    LocalDate getCreatedDate();
    LocalDate getModifiedDate();
    int getViewCount();

    Integer getAuthorId();
}