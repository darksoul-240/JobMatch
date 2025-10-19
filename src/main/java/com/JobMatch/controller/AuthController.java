package com.JobMatch.controller;

import java.security.Principal;
import com.JobMatch.service.UserService;
import com.JobMatch.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String userType,  // NEW: student or company
            Model model
    ) {
        try {
            if ("company".equals(userType)) {
                userService.registerCompany(name, email, password);
            } else {
                userService.registerStudent(name, email, password);
            }
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        // Get the logged-in user's email
        String email = principal.getName();

        // Find the user in the database
        User user = userService.getUserByEmail(email);

        // Pass user to template
        model.addAttribute("user", user);

        return "dashboard";
    }
}