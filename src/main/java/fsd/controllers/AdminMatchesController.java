package fsd.controllers;

import fsd.entities.Match;
import fsd.entities.MatchDetail;
import fsd.entities.User;
import fsd.messages.FlashMessages;
import fsd.services.MatchDetailService;
import fsd.services.MatchService;
import fsd.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminMatchesController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchDetailService matchDetailService;

    @Autowired
    private UserService userService;

    //all matches
    @GetMapping("admin/matches")
    public String showMatchesPage(Model model){

        List<Match> matches = matchService.getAllExistingMatches();

        model.addAttribute("matches", matches);

        return "admin-matches";
    }

    //one specific match
    @GetMapping("admin/match/{id}")
    public String showSpecificMatch(@PathVariable Long id, Model model){

        Optional<Match> optionalMatch = matchService.getById(id);

        if(optionalMatch.isPresent()){

            Match match = optionalMatch.get();

            List<MatchDetail> matchDetails = matchDetailService.getMatchDetailByMatch(id);

            model.addAttribute("match", match);
            model.addAttribute("details", matchDetails);

            return "admin-match";
        }else{
            return "404";
        }
    }

    //add match
    @GetMapping("admin/matches/add")
    public String showAddMatchPage(Model model){

        Match match = new Match();
        model.addAttribute("match", match);
        return "add-match";
    }

    //save match
    @PostMapping("admin/matches/add")
    public String addMatch(@ModelAttribute @Valid Match match, BindingResult result, RedirectAttributes redirAttrs){

        if (result.hasErrors()) {
            return "add-match";
        }

        matchService.save(match);

        redirAttrs.addFlashAttribute("message", FlashMessages.matchAdded());

        return "redirect:/admin/matches";
    }

    //@Get for match update
    @GetMapping("/admin/match/{id}/update")
    public String updateUserPage(@PathVariable Long id, Model model){
        Optional<Match> optionalMatch = matchService.getById(id);

        if(optionalMatch.isPresent()){
            Match match = optionalMatch.get();

            model.addAttribute("match", match);
            return "add-match";
        }else {
            return "404";
        }
    }

    //add match detail under specific match
    @GetMapping("admin/match/{id}/add-detail")
    public String showAddDetailPage(@PathVariable Long id, Model model){

        Optional<Match> optionalMatch = matchService.getById(id);
        List<User> players = userService.getAllCurrentPlayers();

        if(optionalMatch.isPresent()) {

            MatchDetail matchDetail = new MatchDetail();
            Match match = optionalMatch.get();

            matchDetail.setMatch(match);

            model.addAttribute("match", match);
            model.addAttribute("matchDetail", matchDetail);
            model.addAttribute("players", players);

            return "add-match-detail";
        }else{
            return "404";
        }
    }

    //save match detail
    @PostMapping("admin/match/{matchId}/add-detail")
    public String addMatchDetail(Model model, @PathVariable Long matchId, @Valid @ModelAttribute MatchDetail matchDetail, BindingResult result, RedirectAttributes redirAttrs){
        Optional<Match> optionalMatch = matchService.getById(matchId);
        List<User> players = userService.getAllCurrentPlayers();

        if(optionalMatch.isPresent()) {

            Match match = optionalMatch.get();

            if (result.hasErrors()) {

                model.addAttribute("match", match);
                model.addAttribute("matchDetail", matchDetail);
                model.addAttribute("players", players);

                return "add-match-detail";
            }

            matchDetailService.save(matchDetail);

            redirAttrs.addFlashAttribute("message", FlashMessages.matchDetailAdded());

            return "redirect:/admin/match/" + match.getId();
        }else{
            return "404";
        }
    }

    //@Get for modify match detail
    @GetMapping("admin/match/{matchId}/detail/{detailId}/update")
    public String updateMatchDetail(@PathVariable Long matchId, @PathVariable Long detailId, Model model){

        Optional<Match> optionalMatch = matchService.getById(matchId);
        Optional<MatchDetail> optionalMatchDetail = matchDetailService.getById(detailId);
        List<User> players = userService.getAllCurrentPlayers();

        if(optionalMatchDetail.isPresent()){
            MatchDetail matchDetail = optionalMatchDetail.get();
            Match match = optionalMatch.get();

            model.addAttribute("match", match);
            model.addAttribute("matchDetail", matchDetail);
            model.addAttribute("players", players);
            return "add-match-detail";
        }else {
            return "404";
        }
    }

    //delete match
    @GetMapping("admin/match/{id}/delete")
    public String deleteMatch(@PathVariable Long id, RedirectAttributes redirAttrs){

        Optional<Match> optionalMatch = matchService.getById(id);

        List<MatchDetail> detailsToDelete = matchDetailService.getMatchDetailByMatch(id);

        if(optionalMatch.isPresent()){
            Match match = optionalMatch.get();

            for(int i = 0; i < detailsToDelete.size(); i++){
                detailsToDelete.get(i).setDeleted(MatchDetail.Deleted.YES);
                matchDetailService.save(detailsToDelete.get(i));
            }

            matchService.delete(match);
            redirAttrs.addFlashAttribute("message", FlashMessages.matchDeleted());
            return "redirect:/admin/matches";
        }else{
            return "404";
        }
    }

    //delete match detail
    @GetMapping("admin/match/{matchId}/detail/{detailId}/delete")
    public  String deleteMatchDetail(@PathVariable Long detailId, RedirectAttributes redirAttrs){
        Optional<MatchDetail> optionalMatchDetail = matchDetailService.getById(detailId);

        if(optionalMatchDetail.isPresent()){
            MatchDetail matchDetail = optionalMatchDetail.get();

            matchDetailService.delete(matchDetail);
            redirAttrs.addFlashAttribute("message", FlashMessages.matchDetailDeleted());
            return "redirect:/admin/match/{matchId}";
        }else{
            return "404";
        }

    }

}
