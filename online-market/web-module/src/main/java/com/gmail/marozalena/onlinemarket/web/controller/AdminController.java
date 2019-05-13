package com.gmail.marozalena.onlinemarket.web.controller;

import com.gmail.marozalena.onlinemarket.service.RoleServise;
import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/private")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final UserService userService;
    private final RoleServise roleServise;
    private static final String redirectToFirstUsersPage = "redirect:/private/users/1";

    @Autowired
    public AdminController(UserService userService,
                           RoleServise roleServise) {
        this.userService = userService;
        this.roleServise = roleServise;
    }

    @GetMapping("/users")
    public String getUsers() {
        return redirectToFirstUsersPage;
    }

    @GetMapping("/users/{page}")
    public String getUsersWithPage(Model model,
                                   @PathVariable("page") Integer page) {

        model.addAttribute("roles", roleServise.getRoles());
        int pageN;
        if (page == null) {
            pageN = 1;
        } else {
            pageN = page;
        }
        int countPages = userService.getCountPages();
        if (pageN > countPages && countPages > 0) {
            pageN = countPages;
        }
        List<Integer> countOfPages = IntStream.rangeClosed(1, countPages)
                .boxed()
                .collect(Collectors.toList());
        model.addAttribute("pages", countOfPages);
        model.addAttribute("current", pageN);
        model.addAttribute("users", userService.getUsers(pageN));
        return "users";
    }

    @PostMapping(value = "/users/delete")
    public String deleteUsers(@RequestParam("ids") List<Long> idUsers) {
        if (idUsers != null) {
            userService.deleteUsers(idUsers);
        }
        return redirectToFirstUsersPage;
    }

    @PostMapping(value = "/users", params = "action=password")
    public String getPassword(UserDTO userDTO) {
        String password = userService.getRandomPassword();
        logger.info("For user with email: " + userDTO.getEmail() + " was generated new password: " + password);
        return redirectToFirstUsersPage;
    }

    @PostMapping(value = "/users", params = "action=save")
    public String saveUser(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return redirectToFirstUsersPage;
    }

    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "add";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute(value = "user") UserDTO userDTO) {
        userService.addUser(userDTO);
        return redirectToFirstUsersPage;
    }

}
