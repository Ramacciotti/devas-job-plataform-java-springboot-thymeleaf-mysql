package com.ramacciotti.devas.controller;

import com.ramacciotti.devas.dto.UserDTO;
import com.ramacciotti.devas.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }


    @GetMapping
    public String getUsers(Model model) {
        List<UserDTO> users = homeService.getUsers();
        model.addAttribute("users", users);
        return "index";
    }

    @ResponseBody
    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable Long id) {
        try {
            return homeService.getUserPhoto(id);
        } catch (Exception e) {
            log.error("An unexpected error occurred while retrieving the photo for user ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

}
