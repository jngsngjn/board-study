package hello.boardstudy.service.user;

import hello.boardstudy.entity.user.User;
import hello.boardstudy.form.RegisterForm;
import hello.boardstudy.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hello.boardstudy.entity.user.Role.*;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(RegisterForm registerForm) {

        if (userRepository.existsByLoginId(registerForm.getLoginId()) || userRepository.existsByEmail(registerForm.getEmail())) {
            return false;
        }

        User user = new User();
        user.setLoginId(registerForm.getLoginId());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setName(registerForm.getName());
        user.setEmail(registerForm.getEmail());
        user.setRole(registerForm.getLoginId().equals("admin") ? ROLE_ADMIN : ROLE_USER);

        userRepository.save(user);
        return true;
    }
}
