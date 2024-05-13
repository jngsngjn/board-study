package hello.boardstudy.controller.user;

import hello.boardstudy.service.email.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Slf4j
@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-verification-email")
    @ResponseBody
    public String sendVerificationEmail(@RequestParam("email") String email, HttpServletRequest request) {
        log.info("email={}", email);

        // 인증 링크 생성
        String verificationLink = generateVerificationLink(email, request);

        // 이메일 전송
        emailService.sendVerificationEmail(email, verificationLink);

        return "인증 이메일이 전송되었습니다.";
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
