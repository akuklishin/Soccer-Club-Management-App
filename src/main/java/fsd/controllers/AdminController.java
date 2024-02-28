package fsd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @GetMapping("admin")
    public String admin(Model model) {

        return "admin";
    }
}
