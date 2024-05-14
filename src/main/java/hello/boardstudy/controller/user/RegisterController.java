package hello.boardstudy.controller.user;

import hello.boardstudy.form.RegisterForm;
import hello.boardstudy.service.email.EmailService;
import hello.boardstudy.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final EmailService emailService;

    public RegisterController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registerForm";
    }

    @PostMapping
    public String register(@Validated @ModelAttribute RegisterForm registerForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("필드 에러");
            return "registerForm";
        }

        if (!emailService.isVerifiedEmail(registerForm.getEmail())) {
            log.info("인증되지 않은 이메일={}", registerForm.getEmail());
            return "registerForm";
        }

        if (!userService.register(registerForm)) {
            log.info("아이디 또는 이메일 중복");
            return "registerForm";
        }

        return "redirect:/boards";
    }

    @ResponseBody
    @PostMapping("/check-duplicate-id")
    public boolean checkDuplicateId(@RequestParam String loginId) {

        return !userService.isDuplicateId(loginId);
    }
}