package fsd.controllers;

import fsd.entities.User;
import fsd.messages.FlashMessages;
import fsd.services.FileUploadService;
import fsd.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${spring.datasource.region}")
    private String s3Region;

    @Value("${spring.datasource.bucket-name}")
    private String s3Name;

    @GetMapping("admin/users")
    public String allUsersPage(Model model) {

        List<User> players = userService.getAllCurrentPlayers();
        List<User> coaches = userService.getAllCurrentCoaches();
        List<User> admins = userService.getAllCurrentAdmins();
        model.addAttribute("players", players);
        model.addAttribute("coaches", coaches);
        model.addAttribute("admins", admins);

        return "admin-users";
    }

    @GetMapping("/admin/users/register")
    public String showAddUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register-user";
    }

    @PostMapping("/admin/users/register")
    public String addUser(@Valid @ModelAttribute User user, BindingResult result, RedirectAttributes redirAttrs, @RequestParam("profileImage") MultipartFile file) throws IOException {

//        @RequestParam("profileImage")

        if (user.getId() == null) {
            if (userService.exists(user.getEmail())) {
                FieldError usernameTaken = new FieldError("user", "email", "Email is taken");
                result.addError(usernameTaken);
            }

            if (!user.getPassword().equals(user.getRepeatPassword())) {
                FieldError passwordNotMatch = new FieldError("user", "repeatPassword", "Password does not match");
                result.addError(passwordNotMatch);
            } else {
                user.setRepeatPassword("MATCHED");
            }
        }

        if (result.hasErrors()) {
            return "register-user";
        }

        if (file.isEmpty()) {
            user.setImagePath(null);
        } else {
            //Locally
//            user.setImagePath("/img/" + user.getFirstName() + user.getLastName() + ".jpg");
//            File dest = new File("src/main/resources/static/img/" + user.getFirstName() + user.getLastName() + ".jpg");
//            Files.copy(file.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // S3
            String fileName = user.getFirstName() + user.getLastName() + ".jpg"; // Construct file name
            fileUploadService.uploadFileToS3(file, fileName); // Upload file to S3

            // Use s3Name and s3Region from application.properties to construct the S3 URL
            String imagePath = "https://" + s3Name + ".s3." + s3Region + ".amazonaws.com/img/" + fileName;
            user.setImagePath(imagePath);


        }

        userService.save(user);

        redirAttrs.addFlashAttribute("message", FlashMessages.userAdded(user));
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/{id}/update")
    public String updateUserPage(@PathVariable Long id, Model model) {
        Optional<User> optionalUser = userService.getById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            model.addAttribute("user", user);
            return "register-user";
        } else {
            return "404";
        }
    }

    @GetMapping("/admin/user/{id}/delete")
    public String deleteUser(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {

        Optional<User> optionalUser = userService.getById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            userService.delete(user);
            redirAttrs.addFlashAttribute("message", FlashMessages.userDeleted(user));
            return "redirect:/admin/users";
        } else {
            return "404";
        }
    }

}
