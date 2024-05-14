package hello.boardstudy.security;

import hello.boardstudy.entity.user.User;
import hello.boardstudy.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + loginId);
        }
        return new CustomUserDetails(user);
    }
}
