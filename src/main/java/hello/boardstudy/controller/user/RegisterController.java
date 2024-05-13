package hello.boardstudy.controller.user;

import hello.boardstudy.form.RegisterForm;
import hello.boardstudy.repository.user.UserRepository;
import hello.boardstudy.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final UserRepository userRepository;

    public RegisterController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registerForm";
    }

    @PostMapping
    public String register(@Validated @ModelAttribute RegisterForm registerForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registerForm";
        }

        if (!userService.register(registerForm)) {
            return "registerForm";
        }

        return "redirect:/boards";
    }

    @PostMapping("/check-duplicate-id")
    @ResponseBody
    public boolean checkDuplicateId(@RequestParam String loginId) {
        return !userRepository.existsByLoginId(loginId);
    }
}