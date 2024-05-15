package hello.boardstudy.entity.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "failed_attempts", columnDefinition = "INT DEFAULT 0")
    private int failedAttempts;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean locked;

    // 로그인 실패 횟수 증가
    public void incrementFailedAttempts() {
        this.failedAttempts++;
    }
}