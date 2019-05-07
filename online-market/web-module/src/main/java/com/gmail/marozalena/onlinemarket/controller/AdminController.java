package com.gmail.marozalena.onlinemarket.controller;

import com.gmail.marozalena.onlinemarket.ReviewService;
import com.gmail.marozalena.onlinemarket.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public class AdminController {

    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public AdminController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    public String getUsers(Model model) {
        return "users";
    }

    public String getReviews(Model model) {
        return "reviews";
    }
}
