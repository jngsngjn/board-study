package hello.boardstudy.repository.user;

import hello.boardstudy.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    User findByLoginId(String loginId);
}