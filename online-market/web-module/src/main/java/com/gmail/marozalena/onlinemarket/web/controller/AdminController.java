package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.ReviewService;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.ReviewDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/private")
public class AdminController {

    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public AdminController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<UserDTO> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/reviews")
    public String getReviews(Model model) {
        List<ReviewDTO> reviews = reviewService.getReviews();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/add/user")
    public String addUser() {
        return "user";
    }

    @PostMapping("/add/user")
    public String addUser(UserDTO userDTO) {
        userService.addUser(userDTO);
        return "user";
    }
}
