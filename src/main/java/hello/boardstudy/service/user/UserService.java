package hello.boardstudy.service.user;

import hello.boardstudy.entity.user.User;
import hello.boardstudy.form.RegisterForm;
import hello.boardstudy.repository.user.EmailTokenRepository;
import hello.boardstudy.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hello.boardstudy.entity.user.Role.*;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EmailTokenRepository emailTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, EmailTokenRepository emailTokenRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailTokenRepository = emailTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean register(RegisterForm registerForm) {
        log.info("registerForm={}", registerForm);

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
        emailTokenRepository.deleteByEmail(registerForm.getEmail());

        return true;
    }

    public boolean isDuplicateId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
}
