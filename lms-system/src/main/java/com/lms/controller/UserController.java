package com.lms.controller;

import com.lms.dto.UserDto;
import com.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(UserDto userDto) {
        try {
            userService.register(userDto);
            return "redirect:/login?register";
        } catch (IllegalStateException e) {
            return "redirect:/register?error";
        }
    }
} 