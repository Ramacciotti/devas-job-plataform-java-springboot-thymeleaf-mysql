package com.ramacciotti.devas.controller;

import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
        try {
            registerService.saveUser(userDTO);
            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("user", userDTO);
            return "register_form";
        }
    }

    @GetMapping("/form")
    public String formRegisterUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register_form";
    }

}
