package fsd.controllers;

import fsd.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";

    }
}
