package com.example.cinemaT.controller;

import com.example.cinemaT.model.Role;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, HttpSession session) {
        Role role = "admin".equalsIgnoreCase(username) ? Role.ADMIN : Role.USER;
        String displayName = "admin".equalsIgnoreCase(username) ? "Admin" : "Ivan";

        session.setAttribute("currentUser", displayName);
        session.setAttribute("currentRole", role.name());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}