package hello.boardstudy.entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String loginId;
    private String password;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

}