package fsd.controllers;

import fsd.entities.Match;
import fsd.entities.MatchDetail;
import fsd.services.MatchDetailService;
import fsd.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class MatchesController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchDetailService matchDetailService;

    @GetMapping("/match/{id}")
    public String showSpecificMatch(@PathVariable Long id, Model model) {

        Optional<Match> optionalMatch = matchService.getById(id);

        if (optionalMatch.isPresent()) {

            Match match = optionalMatch.get();

            List<MatchDetail> matchDetails = matchDetailService.getMatchDetailByMatch(id);

            model.addAttribute("match", match);
            model.addAttribute("details", matchDetails);

            return "match-id";
        } else {
            return "404";
        }
    }
}
