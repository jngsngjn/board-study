package hello.boardstudy.security;

import hello.boardstudy.entity.user.User;
import hello.boardstudy.repository.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;

    // 로그인 실패 시
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws ServletException, IOException {
        String loginId = request.getParameter("username");
        User user = userRepository.findByLoginId(loginId);

        // 사용자가 존재하지 않는 경우
        if (user == null) {
//            setDefaultFailureUrl("/login?error=notFound");
            request.getSession().setAttribute("notFound", "등록되지 않은 계정입니다.");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        // 사용자가 존재하는 경우
        // 이미 실패 횟수가 5회 이상인 경우
        if (user.getFailedAttempts() >= 5) {
            setDefaultFailureUrl("/login?error=locked");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        user.incrementFailedAttempts();
        userRepository.save(user);

        // 실패 횟수가 5회인 경우
        if (user.getFailedAttempts() == 5) {

            user.setLocked(true);
            userRepository.save(user);
            setDefaultFailureUrl("/login?error=locked");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        // 실패 횟수가 5회 미만인 경우
        int remainingAttempts = 5 - user.getFailedAttempts();
        setDefaultFailureUrl("/login?error=true&remainingAttempts=" + remainingAttempts);
        super.onAuthenticationFailure(request, response, exception);
    }
}