package fsd.controllers;

import fsd.entities.Match;
import fsd.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fsd.entities.User;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MatchService matchService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        User user = new User();

        List<Match> matches = matchService.getAllExistingMatches();
        model.addAttribute("matches", matches);
        model.addAttribute("user", user);

        return "home";
    }
}
