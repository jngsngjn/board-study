package hello.boardstudy.controller.user;

import hello.boardstudy.form.RegisterForm;
import hello.boardstudy.service.user.UserService;
import org.springframework.stereotype.Controller;
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

    @PostMapping
    public String register(@ModelAttribute RegisterForm registerForm) {
        if (!userService.register(registerForm)) {
            return "board";
        }

        return "redirect:/boards";
    }
}