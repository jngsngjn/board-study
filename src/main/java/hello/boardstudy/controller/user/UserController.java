package hello.boardstudy.controller.user;

import hello.boardstudy.form.RegisterForm;
import hello.boardstudy.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registerForm";
    }

    @PostMapping
    public String register(@ModelAttribute RegisterForm registerForm) {
        if (!userService.register(registerForm)) {
            return "registerForm";
        }

        return "redirect:/boards";
    }
}