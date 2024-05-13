package hello.boardstudy.service.email;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String email, HttpServletRequest request) {

        String verificationLink = generateVerificationLink(email, request);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("이메일 인증");
        message.setText("안녕하세요,\n\n아래 링크를 클릭하여 이메일 인증을 완료해주세요.\n\n인증 링크: " + verificationLink);

        mailSender.send(message);
    }

    private String generateVerificationLink(String email, HttpServletRequest request) {
        String token = generateToken();
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        return baseUrl + "/verify?email=" + email + "&token=" + token;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}