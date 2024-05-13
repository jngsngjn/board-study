package hello.boardstudy.service.email;

import hello.boardstudy.entity.user.EmailToken;
import hello.boardstudy.repository.user.EmailTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailTokenRepository emailTokenRepository;

    public EmailService(JavaMailSender mailSender, EmailTokenRepository emailTokenRepository) {
        this.mailSender = mailSender;
        this.emailTokenRepository = emailTokenRepository;
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    // 인증 메일 전송
    public void sendVerificationEmail(String email, HttpServletRequest request) {
        String token = generateToken();
        saveToken(email, token);

        String verificationLink = generateLink(token, request);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("이메일 인증");
        message.setText("안녕하세요,\n\n아래 링크를 클릭하여 이메일 인증을 완료해주세요.\n\n인증 링크: " + verificationLink);

        mailSender.send(message);
    }

    // 토큰 저장
    private void saveToken(String email, String token) {
        EmailToken emailToken = new EmailToken();
        emailToken.setToken(token);
        emailToken.setEmail(email);
        emailToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));

        emailTokenRepository.save(emailToken);
    }

    // 인증 링크 생성
    private String generateLink(String token, HttpServletRequest request) {
        return getBaseUrl(request) + "/verify/" + token;
    }

    // 토큰 생성
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private static String getBaseUrl(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getRequestURI(), "");
    }

    public boolean isTokenExpired(String token) {
        EmailToken emailToken = emailTokenRepository.findByToken(token);
        if (emailToken == null) {
            return true; // 토큰이 존재하지 않으면 만료된 것으로 간주
        }

        LocalDateTime expiryDate = emailToken.getExpiryDate();
        return expiryDate.isBefore(LocalDateTime.now());
    }

    public void verifySuccess(String token) {
        emailTokenRepository.updateVerifiedByToken(token);
    }

    @Scheduled(cron = "0 * * * * ?") // 매분 실행
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        emailTokenRepository.deleteByExpiryDateBefore(now);
    }

    public boolean isVerifiedEmail(String email) {
        EmailToken emailToken = emailTokenRepository.findByEmail(email);

        if (emailToken == null || !emailToken.isVerified()) {
            return false;
        }

        return true;
    }

}