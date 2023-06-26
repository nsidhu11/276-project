package trackour.trackour.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import trackour.trackour.models.User;
import trackour.trackour.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepo;

    @PostMapping("")
    public String addUser(@RequestParam Map<String, String> newUser, HttpServletResponse response) {
        System.out.println("Adding new user...");

        String newUsername = newUser.get("username");
        String newPassword = newUser.get("password");
        String newEmail = newUser.get("email");

        userRepo.saveAndFlush(new User(newUsername, newUsername, newPassword, newEmail));
        response.setStatus(201);

        return "";
    }
}
