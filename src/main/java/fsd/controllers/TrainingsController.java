package fsd.controllers;

import fsd.entities.Training;
import fsd.entities.User;
import fsd.messages.FlashMessages;
import fsd.services.TrainingService;
import fsd.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class TrainingsController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private UserService userService;


    @GetMapping("/trainings/{id}")
    public String getTrainingsDisplayed(@PathVariable Long id, Model model) {

        Optional<User> optionalUser = userService.getById(id);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if (user.getPosition().equals(User.Position.GOALKEEPER)) {
                List<Training> trainings = trainingService.getAllGoalkeepersTrainings();

                model.addAttribute("trainings", trainings);
            } else if (user.getPosition().equals(User.Position.DEFENDER)) {
                List<Training> trainings = trainingService.getAllDefendersTrainings();

                model.addAttribute("trainings", trainings);
            } else if (user.getPosition().equals(User.Position.ATTACKER)) {
                List<Training> trainings = trainingService.getAllAttackersTrainings();

                model.addAttribute("trainings", trainings);
            } else {
                List<Training> trainings = trainingService.getAllExistingTrainings();

                model.addAttribute("trainings", trainings);
            }

            model.addAttribute("user", user);
            return "coach-trainings";

        } else {
            return "404";
        }
    }

    @GetMapping("/trainings/archive/{id}")
    public String getTrainingsArchive(@PathVariable Long id, Model model) {

        Optional<User> optionalUser = userService.getById(id);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            if(user.getPosition().equals(User.Position.GOALKEEPER)){
                List<Training> trainings = trainingService.getAllGoalkeepersTrainingsArchive();

                model.addAttribute("trainings", trainings);
            }else if(user.getPosition().equals(User.Position.DEFENDER)){
                List<Training> trainings = trainingService.getAllDefendersTrainingsArchive();

                model.addAttribute("trainings", trainings);
            } else if (user.getPosition().equals(User.Position.ATTACKER)) {
                List<Training> trainings = trainingService.getAllAttackersTrainingsArchive();

                model.addAttribute("trainings", trainings);
            }else {
                List<Training> trainings = trainingService.getTrainingsArchive();

                model.addAttribute("trainings", trainings);
            }

            model.addAttribute("user", user);
            return "trainings-archive";

        }else {
            return "404";
        }
    }

    @GetMapping("/trainings/add/{coachId}")
    public String showAddTrainingPage(@PathVariable Long coachId, Model model) {

        Optional<User> optionalCoach = userService.getById(coachId);

        if (optionalCoach.isPresent()) {

            User coach = optionalCoach.get();

            Training training = new Training();

            if(coach.getPosition() == User.Position.GOALKEEPER){
                training.setPosition(Training.Position.GOALKEEPER);
            }else if(coach.getPosition() == User.Position.DEFENDER){
                training.setPosition(Training.Position.DEFENDER);
            }else if(coach.getPosition() == User.Position.ATTACKER){
                training.setPosition(Training.Position.ATTACKER);
            }

            training.setUser(coach);

            model.addAttribute("coach", coach);
            model.addAttribute("training", training);

            return "add-training";
        } else {
            return "404";
        }


    }

    @PostMapping("/trainings/add/{coachId}")
    public String showAddTrainingPage(Model model, @PathVariable Long coachId, @ModelAttribute @Valid Training training, BindingResult result, RedirectAttributes redirAttrs) {

        Optional<User> optionalUser = userService.getById(coachId);


        if (optionalUser.isPresent()) {

            User coach = optionalUser.get();

            if (result.hasErrors()) {

                model.addAttribute("coach", coach);

                return "add-training";
            }

            trainingService.save(training);

            redirAttrs.addFlashAttribute("message", FlashMessages.trainingAdded());

            return "redirect:/trainings/" + coach.getId();
        } else {
            return "404";
        }
    }

    @GetMapping("/trainings/update/{coachId}/{trainingId}")
    public String updateTraining(@PathVariable Long coachId, @PathVariable Long trainingId, Model model){
        Optional<User> optionalUser = userService.getById(coachId);
        Optional<Training> optionalTraining =trainingService.getById(trainingId);

        if(optionalTraining.isPresent()){

            Training training = optionalTraining.get();
            User coach = optionalUser.get();

            model.addAttribute("training", training);
            model.addAttribute("coach", coach);

            return "add-training";
        }else {
            return "404";
        }
    }

    @GetMapping("/trainings/delete/{coachId}/{trainingId}")
    public String deleteTraining(@PathVariable Long trainingId, @PathVariable Long coachId, RedirectAttributes redirAttrs){
        Optional<User> optionalCoach = userService.getById(coachId);
        Optional<Training> optionalTraining = trainingService.getById(trainingId);

        if(optionalTraining.isPresent()) {
            Training training = optionalTraining.get();
            User coach = optionalCoach.get();

            trainingService.delete(training);

            redirAttrs.addFlashAttribute("message", FlashMessages.trainingDeleted());
            return "redirect:/trainings/" + coach.getId();
        }else {
            return "404";
        }
    }

}
