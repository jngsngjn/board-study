package hello.boardstudy.security;

import hello.boardstudy.entity.user.User;
import hello.boardstudy.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loginId = userDetails.getUsername();
        User user = userRepository.findByLoginId(loginId);

        // 계정이 잠겨있는 경우
        if (user.isLocked() || user.getFailedAttempts() >= 5) {
            response.sendRedirect("/login?error=locked");
        } else {
            // 계정이 잠겨있지 않은 경우 로그인 성공 처리
            user.setFailedAttempts(0);
            userRepository.save(user);
            response.sendRedirect("/boards");
        }
    }
}