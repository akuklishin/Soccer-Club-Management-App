package fsd.controllers;

import fsd.entities.User;
import fsd.services.MatchDetailService;
import fsd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class PlayersController {

    @Autowired
    private UserService userService;

    @Autowired
    private MatchDetailService matchDetailService;

    @GetMapping("/players/all")
    private String showAllplayers(Model model){

        List<User> players = userService.getAllCurrentPlayers();

        model.addAttribute("players", players);

        return "player-all";
    }

    @GetMapping("/player/{id}")
    public String getSpecificPLayer(@PathVariable Long id, Model model){

        Optional<User> optionalPlayer = userService.getById(id);

        if(optionalPlayer.isPresent()) {

            User player = optionalPlayer.get();

            long totalGoals = matchDetailService.totalGoalsByPLayer(player.getId());
            long totalAssists = matchDetailService.totalAssistsByPLayer(player.getId());
            long totalYellows = matchDetailService.totalYellowsByPLayer(player.getId());
            long totalReds = matchDetailService.totalRedsByPLayer(player.getId());

            model.addAttribute("totalGoals", totalGoals);
            model.addAttribute("totalAssists", totalAssists);
            model.addAttribute("totalYellows", totalYellows);
            model.addAttribute("totalReds", totalReds);
            model.addAttribute("player", player);
            return "player-id";
        }else {
            return "404";
        }
    }
}
