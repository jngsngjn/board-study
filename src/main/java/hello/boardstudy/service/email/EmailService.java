package hello.boardstudy.service.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wjdtjdwls98@gmail.com");
        message.setTo(to);
        message.setSubject("이메일 인증");
        message.setText("안녕하세요,\n\n아래 링크를 클릭하여 이메일 인증을 완료해주세요.\n\n인증 링크: " + verificationLink);
        mailSender.send(message);
    }
}