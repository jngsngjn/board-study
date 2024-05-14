package hello.boardstudy.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WriteForm {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}