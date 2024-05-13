package hello.boardstudy.controller.user;

import hello.boardstudy.service.email.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
}