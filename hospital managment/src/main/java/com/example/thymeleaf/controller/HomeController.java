package com.example.thymeleaf.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHome() {
        return "home";
    }
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // Retrieves the role of the authenticated user

        return switch (role) {
            case "ROLE_ADMIN" -> "redirect:/admin/home";
            case "ROLE_DOCTOR" -> "redirect:/doctor/home";
            case "ROLE_PATIENT" -> "redirect:/patient/home";
            default ->
                    "redirect:/";
        };

    }

}
