package fsd.controllers;

import fsd.entities.Match;
import fsd.entities.User;
import fsd.services.MatchDetailService;
import fsd.services.MatchService;
import fsd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class APIController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private UserService userService;

    @Autowired
    private MatchDetailService matchDetailService;

    @GetMapping("/api/matches")
    @ResponseBody
    public List<Match> getMatches() {
        return matchService.getAllExistingMatchesByOldest();
    }

    @GetMapping("/api/players/{id}")
    @ResponseBody
    public Map<String, Object> getPlayer(@PathVariable Long id) {
        Optional<User> optionalPlayer = userService.getById(id);

        if (optionalPlayer.isPresent()) {
            User player = optionalPlayer.get();

            long totalGoals = matchDetailService.totalGoalsByPLayer(player.getId());
            long totalAssists = matchDetailService.totalAssistsByPLayer(player.getId());
            long totalYellows = matchDetailService.totalYellowsByPLayer(player.getId());
            long totalReds = matchDetailService.totalRedsByPLayer(player.getId());

            Map<String, Object> playerData = new HashMap<>();
            playerData.put("player", player);
            playerData.put("totalGoals", totalGoals);
            playerData.put("totalAssists", totalAssists);
            playerData.put("totalYellows", totalYellows);
            playerData.put("totalReds", totalReds);

            return playerData;
        } else {
            return null;
        }
    }

    @GetMapping("/api/players")
    @ResponseBody
    public List<Map<String, Object>> getAllPlayers() {
        List<User> allPlayers = userService.getAllCurrentPlayers();

        return allPlayers.stream().map(player -> {
            long totalGoals = matchDetailService.totalGoalsByPLayer(player.getId());
            long totalAssists = matchDetailService.totalAssistsByPLayer(player.getId());
            long totalYellows = matchDetailService.totalYellowsByPLayer(player.getId());
            long totalReds = matchDetailService.totalRedsByPLayer(player.getId());

            Map<String, Object> playerData = new HashMap<>();
            playerData.put("player", player);
            playerData.put("totalGoals", totalGoals);
            playerData.put("totalAssists", totalAssists);
            playerData.put("totalYellows", totalYellows);
            playerData.put("totalReds", totalReds);

            return playerData;
        }).collect(Collectors.toList());
    }
}