package hello.boardstudy.controller.user;

import hello.boardstudy.service.email.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-verification-email")
    @ResponseBody
    public ResponseEntity<Void> sendVerificationEmail(@RequestParam String email, HttpServletRequest request) {
        log.info("email={}", email);

        emailService.sendVerificationEmail(email, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/verify/{token}")
    public String verifyEmail(@PathVariable String token) {

        // 토큰 만료
        if (emailService.isTokenExpired(token)) {
            return "verifyError";
        }

        emailService.verifySuccess(token);

        return "verifySuccess";
    }
}